package com.altamirano.fabricio.core.swipe

import android.R
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Rect
import android.os.SystemClock
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewConfiguration
import android.widget.AbsListView
import android.widget.ListView
import java.util.*

class SwipeActionTouchListener(listView: ListView, callbacks: ActionCallbacks) : OnTouchListener {
    // Cached ViewConfiguration and system-wide constant values
    private val mSlop: Int
    private val mMinFlingVelocity: Int
    private val mMaxFlingVelocity: Int
    private val mAnimationTime: Long

    // Fixed properties
    private val mListView: ListView
    private val mCallbacks: ActionCallbacks
    private var mViewWidth = 1f // 1 and not 0 to prevent dividing by zero
    private var mFadeOut = false
    private var mFixedBackgrounds = false
    private var mDimBackgrounds = false
    private var mNormalSwipeFraction = 0.25f
    private var mFarSwipeFraction = 0.5f

    // Transient properties
    private val mPendingDismisses: MutableList<PendingDismissData> = ArrayList()
    private var mDismissAnimationRefCount = 0
    private var mDownX = 0f
    private var mDownY = 0f
    private var mSwiping = false
    private var mSwipingSlop = 0
    private var mVelocityTracker: VelocityTracker? = null
    private var mDownPosition = 0
    private var mDownView: View? = null
    private var mDownViewGroup: SwipeViewGroup? = null
    private var mPaused = false
    private var mDirection = SwipeDirection.DIRECTION_NEUTRAL
    private var mFar = false

    /**
     * The callback interface used by [SwipeActionTouchListener] to inform its client
     * about a successful dismissal of one or more list item positions.
     */
    interface ActionCallbacks {
        /**
         * Called to determine whether the given position can be dismissed.
         *
         * @param position the position of the item that was swiped
         * @param direction the direction in which the swipe happened
         * @return boolean indicating whether the item has actions
         */
        fun hasActions(position: Int, direction: SwipeDirection?): Boolean

        /**
         * Called when the user has swiped a list item position.
         * The listener will wait for this method to return before starting the dismiss animation
         * or the reappear animation. Please perform any heavy computations in an ASyncTask to avoid
         * blocking the interface.
         *
         * @param listView The originating [ListView].
         * @param position The position to perform the action on, sorted in descending  order
         * for convenience.
         * @param direction The type of swipe that triggered the action
         * @return boolean that indicates whether the list item should be dismissed or shown again.
         */
        fun onPreAction(listView: ListView?, position: Int, direction: SwipeDirection?): Boolean

        /**
         * Called after the dismiss or reappear animation of a swiped item has finished.
         *
         * @param listView The originating [ListView].
         * @param position The position to perform the action on, sorted in descending  order
         * for convenience.
         * @param direction The type of swipe that triggered the action
         */
        fun onAction(listView: ListView?, position: IntArray?, direction: Array<SwipeDirection?>?)

        /**
         * Called once the user touches the screen and starts swiping in any direction
         *
         * @param listView The originating [ListView].
         * @param position The position the user is swiping at
         * @param direction The type of swipe that triggered the action
         */
        fun onSwipeStarted(listView: ListView?, position: Int, direction: SwipeDirection?)

        /**
         * Called once the swiping motion ended (user lifted finger from the screen)
         *
         * @param listView The originating [ListView].
         * @param position The position started swiping on
         * @param direction The type of swipe that triggered the action
         */
        fun onSwipeEnded(listView: ListView?, position: Int, direction: SwipeDirection?)
    }

    /**
     * Enables or disables (pauses or resumes) watching for swipe-to-dismiss gestures.
     *
     * @param enabled Whether or not to watch for gestures.
     */
    fun setEnabled(enabled: Boolean) {
        mPaused = !enabled
    }

    /**
     * Returns an [AbsListView.OnScrollListener] to be added to the [ ] using [ListView.setOnScrollListener].
     * If a scroll listener is already assigned, the caller should still pass scroll changes through
     * to this listener. This will ensure that this [SwipeActionTouchListener] is
     * paused during list view scrolling.
     *
     * @see SwipeActionTouchListener
     */
    fun makeScrollListener(): AbsListView.OnScrollListener {
        return object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(absListView: AbsListView, scrollState: Int) {
                setEnabled(scrollState != AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
            }

            override fun onScroll(absListView: AbsListView, i: Int, i1: Int, i2: Int) {}
        }
    }

    /**
     * Set whether the list item should fade out when swiping or not.
     * The default value for this property is false
     *
     * @param fadeOut true for a fade out, false for no fade out.
     */
    fun setFadeOut(fadeOut: Boolean) {
        mFadeOut = fadeOut
    }

    /**
     * Set whether the backgrounds should be fixed or swipe in from the side
     * The default value for this property is false: backgrounds will swipe in
     *
     * @param fixedBackgrounds true for fixed backgrounds, false for swipe in
     */
    fun setFixedBackgrounds(fixedBackgrounds: Boolean) {
        mFixedBackgrounds = fixedBackgrounds
    }

    /**
     * Set whether the backgrounds should be dimmed while in no-trigger zone
     * The default value for this property is false: backgrounds will not dim
     *
     * @param dimBackgrounds true for fixed backgrounds, false for swipe in
     */
    fun setDimBackgrounds(dimBackgrounds: Boolean) {
        mDimBackgrounds = dimBackgrounds
    }

    /**
     * Set the fraction of the View Width that needs to be swiped before it is counted as a far swipe
     *
     * @param farSwipeFraction float between 0 and 1, should be equal to or greater than normalSwipeFraction
     */
    fun setFarSwipeFraction(farSwipeFraction: Float) {
        mFarSwipeFraction = farSwipeFraction
    }

    /**
     * Set the fraction of the View Width that needs to be swiped before it is counted as a normal swipe
     *
     * @param normalSwipeFraction float between 0 and 1, should be equal to or less than farSwipeFraction
     */
    fun setNormalSwipeFraction(normalSwipeFraction: Float) {
        mNormalSwipeFraction = normalSwipeFraction
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (mViewWidth < 2) {
            mViewWidth = mListView.width.toFloat()
        }
        when (motionEvent.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (mPaused) {
                    return false
                }

                // TODO: ensure this is a finger, and set a flag

                // Find the child view that was touched (perform a hit test)
                val rect = Rect()
                val childCount = mListView.childCount
                val listViewCoords = IntArray(2)
                mListView.getLocationOnScreen(listViewCoords)
                val x = motionEvent.rawX.toInt() - listViewCoords[0]
                val y = motionEvent.rawY.toInt() - listViewCoords[1]
                var child: View
                var i = 0
                while (i < childCount) {
                    child = mListView.getChildAt(i)
                    child.getHitRect(rect)
                    if (rect.contains(x, y)) {
                        try {
                            mDownViewGroup = child as SwipeViewGroup
                            mDownView =
                                if (mFixedBackgrounds) mDownViewGroup!!.contentView else child
                            if (!mFixedBackgrounds) mDownViewGroup!!.translateBackgrounds()
                        } catch (e: Exception) {
                            mDownView = child
                        }
                        break
                    }
                    i++
                }
                if (mDownView != null) {
                    mDownX = motionEvent.rawX
                    mDownY = motionEvent.rawY
                    mDownPosition = mListView.getPositionForView(mDownView)
                    mVelocityTracker = VelocityTracker.obtain()
                    mVelocityTracker?.addMovement(motionEvent)
                }
                return false
            }
            MotionEvent.ACTION_CANCEL -> {
                if (mVelocityTracker == null) {
                    return false
                }
                if (mDownView != null && mSwiping) {
                    // cancel
                    mDownView!!.animate()
                        .translationX(0f)
                        .alpha(1f)
                        .setDuration(mAnimationTime)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                mDownViewGroup!!.showBackground(
                                    SwipeDirection.DIRECTION_NEUTRAL,
                                    false
                                )
                            }
                        })
                }
                mVelocityTracker!!.recycle()
                mVelocityTracker = null
                mDownX = 0f
                mDownY = 0f
                mDownView = null
                mDownPosition = ListView.INVALID_POSITION
                mSwiping = false
                mDirection = SwipeDirection.DIRECTION_NEUTRAL
                mFar = false
            }
            MotionEvent.ACTION_UP -> {
                if (mVelocityTracker == null) {
                    return false
                }
                mCallbacks.onSwipeEnded(mListView, mDownPosition, mDirection)
                val deltaX = motionEvent.rawX - mDownX
                mVelocityTracker!!.addMovement(motionEvent)
                mVelocityTracker!!.computeCurrentVelocity(1000)
                val velocityX = mVelocityTracker!!.xVelocity
                val absVelocityX = Math.abs(velocityX)
                val absVelocityY = Math.abs(mVelocityTracker!!.yVelocity)
                var dismiss = false
                var dismissRight = false
                if (mCallbacks.hasActions(mDownPosition, mDirection)) {
                    if (Math.abs(deltaX) > mViewWidth * mNormalSwipeFraction && mSwiping) {
                        dismiss = true
                        dismissRight = deltaX > 0
                    } else if (mMinFlingVelocity <= absVelocityX && absVelocityX <= mMaxFlingVelocity && absVelocityY < absVelocityX && mSwiping) {
                        // dismiss only if flinging in the same direction as dragging
                        dismiss = velocityX < 0 == deltaX < 0
                        dismissRight = mVelocityTracker!!.xVelocity > 0
                    }
                }
                if (dismiss && mDownPosition != ListView.INVALID_POSITION) {
                    // dismiss
                    val downView = mDownView // mDownView gets null'd before animation ends
                    val downPosition = mDownPosition
                    val direction = mDirection
                    ++mDismissAnimationRefCount
                    mDownView!!.animate()
                        .translationX(if (dismissRight) mViewWidth else -mViewWidth.toFloat())
                        .alpha(if (mFadeOut) 0f else 1f)
                        .setDuration(mAnimationTime)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                val performDismiss = mCallbacks.onPreAction(
                                    mListView,
                                    downPosition,
                                    direction
                                )
                                if (performDismiss) performDismiss(
                                    downView,
                                    downPosition,
                                    direction
                                ) else slideBack(downView, downPosition, direction)
                            }
                        })
                } else {
                    // cancel
                    mDownView!!.animate()
                        .translationX(0f)
                        .alpha(1f)
                        .setDuration(mAnimationTime)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                mDownViewGroup!!.showBackground(
                                    SwipeDirection.DIRECTION_NEUTRAL,
                                    false
                                )
                            }
                        })
                }
                if (mVelocityTracker != null) mVelocityTracker!!.recycle()
                mVelocityTracker = null
                mDownX = 0f
                mDownY = 0f
                mDownView = null
                mDownPosition = ListView.INVALID_POSITION
                mSwiping = false
                mDirection = SwipeDirection.DIRECTION_NEUTRAL
                mFar = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (mVelocityTracker == null || mPaused) {
                    return false
                }
                mVelocityTracker!!.addMovement(motionEvent)
                val deltaX = motionEvent.rawX - mDownX
                val deltaY = motionEvent.rawY - mDownY
                if (!mSwiping && Math.abs(deltaX) > mSlop && Math.abs(deltaY) < Math.abs(deltaX) / 2) {
                    mSwiping = true
                    mSwipingSlop = if (deltaX > 0) mSlop else -mSlop
                    mListView.requestDisallowInterceptTouchEvent(true)

                    // Cancel ListView's touch (un-highlighting the item)
                    val cancelEvent = MotionEvent.obtain(motionEvent)
                    cancelEvent.action = MotionEvent.ACTION_CANCEL or
                            (motionEvent.actionIndex
                                    shl MotionEvent.ACTION_POINTER_INDEX_SHIFT)
                    mListView.onTouchEvent(cancelEvent)
                    cancelEvent.recycle()
                }
                if (mSwiping) {
                    mCallbacks.onSwipeStarted(mListView, mDownPosition, mDirection)
                    if (mDirection.isLeft && deltaX > 0 || mDirection.isRight && deltaX < 0) mFar =
                        false
                    if (!mFar && Math.abs(deltaX) > mViewWidth * mFarSwipeFraction) mFar = true
                    mDirection =
                        if (!mFar) if (deltaX > 0) SwipeDirection.DIRECTION_NORMAL_RIGHT else SwipeDirection.DIRECTION_NORMAL_LEFT else if (deltaX > 0) SwipeDirection.DIRECTION_FAR_RIGHT else SwipeDirection.DIRECTION_FAR_LEFT
                    if (mCallbacks.hasActions(mDownPosition, mDirection)) {
                        mDownViewGroup!!.showBackground(
                            mDirection,
                            mDimBackgrounds && Math.abs(deltaX) < mViewWidth * mNormalSwipeFraction
                        )
                        mDownView!!.translationX = deltaX - mSwipingSlop
                        if (mFadeOut) mDownView!!.alpha =
                            Math.max(0f, Math.min(1f, 1f - 2f * Math.abs(deltaX) / mViewWidth))
                        mListView.invalidate()
                        return true
                    }
                }
            }
        }
        return false
    }

    internal inner class PendingDismissData(
        var position: Int,
        var direction: SwipeDirection,
        var view: View?
    ) : Comparable<PendingDismissData> {
        override fun compareTo(other: PendingDismissData): Int {
            // Sort by descending position
            return other.position - position
        }
    }

    private fun slideBack(slideInView: View?, downPosition: Int, direction: SwipeDirection) {
        mPendingDismisses.add(PendingDismissData(downPosition, direction, slideInView))
        slideInView!!.translationX = slideInView.translationX
        slideInView.animate()
            .translationX(0f)
            .alpha(1f)
            .setDuration(mAnimationTime)
            .setListener(createAnimatorListener())
    }

    private fun performDismiss(
        dismissView: View?,
        dismissPosition: Int,
        direction: SwipeDirection
    ) {
        // Animate the dismissed list item to zero-height and fire the dismiss callback when
        // all dismissed list item animations have completed. This triggers layout on each animation
        // frame; in the future we may want to do something smarter and more performant.
        val lp = dismissView!!.layoutParams
        val originalHeight = dismissView.height
        val animator = ValueAnimator.ofInt(originalHeight, 1).setDuration(mAnimationTime)
        animator.addListener(createAnimatorListener())
        animator.addUpdateListener { valueAnimator ->
            lp.height = (valueAnimator.animatedValue as Int)
            dismissView.layoutParams = lp
        }
        mPendingDismisses.add(PendingDismissData(dismissPosition, direction, dismissView))
        animator.start()
    }

    private fun createAnimatorListener(): AnimatorListenerAdapter {
        return object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                --mDismissAnimationRefCount
                if (mDismissAnimationRefCount == 0) {
                    // No active animations, process all pending dismisses.
                    // Sort by descending position
                    Collections.sort(mPendingDismisses)
                    val dismissPositions = IntArray(mPendingDismisses.size)
                    val dismissDirections = arrayOfNulls<SwipeDirection>(mPendingDismisses.size)
                    for (i in mPendingDismisses.indices.reversed()) {
                        dismissPositions[i] = mPendingDismisses[i].position
                        dismissDirections[i] = mPendingDismisses[i].direction
                    }
                    mCallbacks.onAction(mListView, dismissPositions, dismissDirections)

                    // Reset mDownPosition to avoid MotionEvent.ACTION_UP trying to start a dismiss
                    // animation with a stale position
                    mDownPosition = ListView.INVALID_POSITION
                    for (pendingDismiss in mPendingDismisses) {
                        // Reset view presentation
                        pendingDismiss.view!!.alpha = 1f
                        pendingDismiss.view!!.translationX = 0f
                        pendingDismiss.view!!.layoutParams = AbsListView.LayoutParams(
                            AbsListView.LayoutParams.MATCH_PARENT,
                            AbsListView.LayoutParams.WRAP_CONTENT
                        )
                    }

                    // Send a cancel event
                    val time = SystemClock.uptimeMillis()
                    val cancelEvent = MotionEvent.obtain(
                        time, time,
                        MotionEvent.ACTION_CANCEL, 0f, 0f, 0
                    )
                    mListView.dispatchTouchEvent(cancelEvent)
                    mDownViewGroup!!.showBackground(SwipeDirection.DIRECTION_NEUTRAL, false)
                    mPendingDismisses.clear()
                }
            }
        }
    }

    /**
     * Constructs a new swipe-to-dismiss touch listener for the given list view.
     *
     * @param listView The list view whose items should be dismissable.
     * @param callbacks The callback to trigger when the user has indicated that she would like to
     * dismiss one or more list items.
     */
    init {
        val vc = ViewConfiguration.get(listView.context)
        mSlop = vc.scaledTouchSlop
        mMinFlingVelocity = vc.scaledMinimumFlingVelocity * 16
        mMaxFlingVelocity = vc.scaledMaximumFlingVelocity
        mAnimationTime = listView.context.resources.getInteger(
            R.integer.config_shortAnimTime
        ).toLong()
        mListView = listView
        mCallbacks = callbacks
    }
}
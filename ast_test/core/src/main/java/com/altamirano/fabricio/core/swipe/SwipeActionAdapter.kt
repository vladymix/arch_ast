package com.altamirano.fabricio.core.swipe

import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ListView
import com.altamirano.fabricio.core.swipe.SwipeActionTouchListener.ActionCallbacks
import java.util.*

class SwipeActionAdapter(baseAdapter: BaseAdapter) : DecoratorAdapter(baseAdapter),
    ActionCallbacks {
    private var mListView: ListView? = null
    private var mTouchListener: SwipeActionTouchListener? = null
    protected var mSwipeActionListener: SwipeActionListener? = null
    private var mFadeOut = false
    private var mFixedBackgrounds = false
    private var mDimBackgrounds = false
    private var mFarSwipeFraction = 0.5f
    private var mNormalSwipeFraction = 0.25f
    protected var mBackgroundResIds = HashMap<SwipeDirection, Int>()
    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var output = convertView as SwipeViewGroup
        if (output == null) {
            output = SwipeViewGroup(parent.context)
            for ((key, value) in mBackgroundResIds) {
                output.addBackground(View.inflate(parent.context, value, null), key)
            }
            output.setSwipeTouchListener(mTouchListener)

            output.contentView = super.getView(position, output.contentView!!, output)
        }

        return output
    }

    /**
     * SwipeActionTouchListener.ActionCallbacks callback
     * We just link it through to our own interface
     *
     * @param position  the position of the item that was swiped
     * @param direction the direction in which the swipe has happened
     * @return boolean indicating whether the item has actions
     */
    override fun hasActions(position: Int, direction: SwipeDirection?): Boolean {
        return mSwipeActionListener != null && mSwipeActionListener!!.hasActions(
            position,
            direction
        )
    }

    /**
     * SwipeActionTouchListener.ActionCallbacks callback
     * We just link it through to our own interface
     *
     * @param listView  The originating [ListView].
     * @param position  The position to perform the action on, sorted in descending  order
     * for convenience.
     * @param direction The type of swipe that triggered the action
     * @return boolean that indicates whether the list item should be dismissed or shown again.
     */
    override fun onPreAction(
        listView: ListView?,
        position: Int,
        direction: SwipeDirection?
    ): Boolean {
        return mSwipeActionListener != null && mSwipeActionListener!!.shouldDismiss(
            position,
            direction
        )
    }

    /**
     * SwipeActionTouchListener.ActionCallbacks callback
     * We just link it through to our own interface
     *
     * @param listView  The originating [ListView].
     * @param position  The positions to perform the action on, sorted in descending  order
     * for convenience.
     * @param direction The type of swipe that triggered the action.
     */
    override fun onAction(
        listView: ListView?,
        position: IntArray?,
        direction: Array<SwipeDirection?>?
    ) {
        if (mSwipeActionListener != null) mSwipeActionListener!!.onSwipe(position, direction)
    }

    /**
     * Called once the user touches the screen and starts swiping in any direction
     *
     * @param listView  The originating [ListView].
     * @param position  The position to perform the action on, sorted in descending  order
     * for convenience.
     * @param direction The type of swipe that triggered the action
     */
    override fun onSwipeStarted(listView: ListView?, position: Int, direction: SwipeDirection?) {
        if (mSwipeActionListener != null) mSwipeActionListener!!.onSwipeStarted(
            listView,
            position,
            direction
        )
    }

    /**
     * Called once the swiping motion ended (user lifted finger)
     *
     * @param listView  The originating [ListView].
     * @param position  The position to perform the action on, sorted in descending  order
     * for convenience.
     * @param direction The type of swipe that triggered the action
     */
    override fun onSwipeEnded(listView: ListView?, position: Int, direction: SwipeDirection?) {
        if (mSwipeActionListener != null) mSwipeActionListener!!.onSwipeEnded(
            listView,
            position,
            direction
        )
    }

    /**
     * Set whether items should have a fadeOut animation
     *
     * @param mFadeOut true makes items fade out with a swipe (opacity to 0)
     * @return A reference to the current instance so that commands can be chained
     */
    fun setFadeOut(mFadeOut: Boolean): SwipeActionAdapter {
        this.mFadeOut = mFadeOut
        if (mListView != null) mTouchListener!!.setFadeOut(mFadeOut)
        return this
    }

    /**
     * Set whether the backgrounds should be fixed or swipe in from the side
     * The default value for this property is false: backgrounds will swipe in
     *
     * @param fixedBackgrounds true for fixed backgrounds, false for swipe in
     */
    fun setFixedBackgrounds(fixedBackgrounds: Boolean): SwipeActionAdapter {
        mFixedBackgrounds = fixedBackgrounds
        if (mListView != null) mTouchListener!!.setFixedBackgrounds(fixedBackgrounds)
        return this
    }

    /**
     * Set whether the backgrounds should be dimmed when in no-trigger zone
     * The default value for this property is false: backgrounds will not dim
     *
     * @param dimBackgrounds true for dimmed backgrounds, false for no opacity change
     */
    fun setDimBackgrounds(dimBackgrounds: Boolean): SwipeActionAdapter {
        mDimBackgrounds = dimBackgrounds
        if (mListView != null) mTouchListener!!.setDimBackgrounds(dimBackgrounds)
        return this
    }

    /**
     * Set the fraction of the View Width that needs to be swiped before it is counted as a far swipe
     *
     * @param farSwipeFraction float between 0 and 1
     */
    fun setFarSwipeFraction(farSwipeFraction: Float): SwipeActionAdapter {
        require(!(farSwipeFraction < 0 || farSwipeFraction > 1)) { "Must be a float between 0 and 1" }
        mFarSwipeFraction = farSwipeFraction
        if (mListView != null) mTouchListener!!.setFarSwipeFraction(farSwipeFraction)
        return this
    }

    /**
     * Set the fraction of the View Width that needs to be swiped before it is counted as a normal swipe
     *
     * @param normalSwipeFraction float between 0 and 1
     */
    fun setNormalSwipeFraction(normalSwipeFraction: Float): SwipeActionAdapter {
        require(!(normalSwipeFraction < 0 || normalSwipeFraction > 1)) { "Must be a float between 0 and 1" }
        mNormalSwipeFraction = normalSwipeFraction
        if (mListView != null) mTouchListener!!.setNormalSwipeFraction(normalSwipeFraction)
        return this
    }

    /**
     * We need the ListView to be able to modify it's OnTouchListener
     *
     * @param listView the ListView to which the adapter will be attached
     * @return A reference to the current instance so that commands can be chained
     */
    fun setListView(listView: ListView): SwipeActionAdapter {
        mListView = listView
        mTouchListener = SwipeActionTouchListener(listView, this)
        mListView!!.setOnTouchListener(mTouchListener)
        mListView!!.setOnScrollListener(mTouchListener!!.makeScrollListener())
        mListView!!.clipChildren = false
        mTouchListener!!.setFadeOut(mFadeOut)
        mTouchListener!!.setDimBackgrounds(mDimBackgrounds)
        mTouchListener!!.setFixedBackgrounds(mFixedBackgrounds)
        mTouchListener!!.setNormalSwipeFraction(mNormalSwipeFraction)
        mTouchListener!!.setFarSwipeFraction(mFarSwipeFraction)
        return this
    }

    /**
     * Getter that is just here for completeness
     *
     * @return the current ListView
     */
    val listView: AbsListView?
        get() = mListView

    /**
     * Add a background image for a certain callback. The key for the background must be one of the
     * directions from the SwipeDirections class.
     *
     * @param key   the identifier of the callback for which this resource should be shown
     * @param resId the resource Id of the background to add
     * @return A reference to the current instance so that commands can be chained
     */
    fun addBackground(key: SwipeDirection, resId: Int): SwipeActionAdapter {
        if (SwipeDirection.allDirections.contains(key)) mBackgroundResIds[key] =
            resId
        return this
    }

    /**
     * Set the listener for swipe events
     *
     * @param mSwipeActionListener class listening to swipe events
     * @return A reference to the current instance so that commands can be chained
     */
    fun setSwipeActionListener(mSwipeActionListener: SwipeActionListener?): SwipeActionAdapter {
        this.mSwipeActionListener = mSwipeActionListener
        return this
    }

    /**
     * Interface that listeners of swipe events should implement
     */
    interface SwipeActionListener {
        fun hasActions(position: Int, direction: SwipeDirection?): Boolean
        fun shouldDismiss(position: Int, direction: SwipeDirection?): Boolean
        fun onSwipe(position: IntArray?, direction: Array<SwipeDirection?>?)
        fun onSwipeEnded(listView: ListView?, position: Int, direction: SwipeDirection?)
        fun onSwipeStarted(listView: ListView?, position: Int, direction: SwipeDirection?)
    }
}
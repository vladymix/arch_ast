package com.altamirano.fabricio.core.swipe

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Checkable
import android.widget.FrameLayout
import java.util.*

class SwipeViewGroup : FrameLayout, Checkable {
    /**
     * Returns the current contentView of the Layout
     *
     * @return contentView of the Layout
     */
    var contentView: View? = null
    private var visibleView = SwipeDirection.DIRECTION_NEUTRAL
    private val mBackgroundMap = HashMap<SwipeDirection, View?>()
    private var swipeTouchListener: OnTouchListener? = null
    private var checked = false
    private var activated = false

    constructor(context: Context?) : super(context!!) {
        initialize()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        initialize()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    ) {
        initialize()
    }

    /**
     * Common code for all the constructors
     */
    private fun initialize() {
        this.isFocusable = false
        // Allows click events to reach the ListView in case the row has a clickable View like a Button
        // FIXME: probably messes with accessibility. Doesn't fix root cause (see onTouchEvent)
        descendantFocusability = FOCUS_BLOCK_DESCENDANTS
    }

    /**
     * Add a View to the background of the Layout. The background should have the same height
     * as the contentView
     *
     * @param background The View to be added to the Layout
     * @param direction The key to be used to find it again
     * @return A reference to the a layout so commands can be chained
     */
    fun addBackground(background: View, direction: SwipeDirection): SwipeViewGroup {
        if (mBackgroundMap[direction] != null) removeView(mBackgroundMap[direction])
        background.visibility = INVISIBLE
        mBackgroundMap[direction] = background
        addView(background)
        return this
    }

    /**
     * Show the View linked to a key. Don't do anything if the key is not found
     *
     * @param direction The key of the View to be shown
     * @param dimBackground Indicates whether the background should be dimmed
     */
    fun showBackground(direction: SwipeDirection, dimBackground: Boolean) {
        if (SwipeDirection.DIRECTION_NEUTRAL != direction && mBackgroundMap[direction] == null) return
        if (SwipeDirection.DIRECTION_NEUTRAL != visibleView) mBackgroundMap[visibleView]!!.visibility =
            INVISIBLE
        if (SwipeDirection.DIRECTION_NEUTRAL != direction) {
            mBackgroundMap[direction]!!.visibility = VISIBLE
            mBackgroundMap[direction]!!.setAlpha(if (dimBackground) 0.4f else 1f)
        }
        visibleView = direction
    }

    /**
     * Add a contentView to the Layout
     *
     * @param contentView The View to be added
     * @return A reference to the layout so commands can be chained
     */
    fun setContentView(contentView: View?): SwipeViewGroup {
        if (this.contentView != null) removeView(contentView)
        addView(contentView)
        this.contentView = contentView
        return this
    }

    /**
     * Move all backgrounds to the edge of the Layout so they can be swiped in
     */
    fun translateBackgrounds() {
        this.clipChildren = false
        for ((key, value) in mBackgroundMap) {
            val signum = if (key.isLeft) 1 else -1
            value!!.translationX = signum * value.width.toFloat()
        }
    }

    /**
     * Set a touch listener the SwipeViewGroup will watch: once the OnTouchListener is interested in
     * events, the SwipeViewGroup will stop propagating touch events to its children
     *
     * @param swipeTouchListener The OnTouchListener to watch
     * @return A reference to the layout so commands can be chained
     */
    fun setSwipeTouchListener(swipeTouchListener: OnTouchListener?): SwipeViewGroup {
        this.swipeTouchListener = swipeTouchListener
        return this
    }

    override fun getTag(): Any? {
        return if (contentView != null) contentView!!.tag else null
    }

    override fun setTag(tag: Any) {
        if (contentView != null) contentView!!.tag = tag
    }

    override fun getTag(key: Int): Any? {
        return if (contentView != null) contentView!!.getTag(key) else null
    }

    override fun setTag(key: Int, tag: Any) {
        if (contentView != null) contentView!!.setTag(key, tag)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // Start tracking the touch when a child is processing it
        return super.onInterceptTouchEvent(ev) || swipeTouchListener!!.onTouch(this, ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        // Finish the swipe gesture: our parent will no longer do it if this function is called
        return swipeTouchListener!!.onTouch(this, ev)
    }

    override fun setChecked(checked: Boolean) {
        this.checked = checked
        if (contentView != null && contentView is Checkable) {
            (contentView as Checkable).isChecked = checked
        }
    }

    override fun isChecked(): Boolean {
        return checked
    }

    override fun setActivated(activated: Boolean) {
        this.activated = activated
        if (contentView != null) contentView!!.isActivated = activated
    }

    override fun isActivated(): Boolean {
        return activated
    }

    override fun toggle() {
        this.isChecked = !checked
    }
}
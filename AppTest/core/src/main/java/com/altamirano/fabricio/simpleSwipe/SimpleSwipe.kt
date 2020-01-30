
package com.altamirano.fabricio.simpleSwipe;

import android.content.Context
import android.graphics.*
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import java.util.*
import kotlin.collections.ArrayList

abstract class SwipeToValidateAdapter(var context: Context,
                                      private var recyclerView: RecyclerView) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.ANIMATION_TYPE_SWIPE_CANCEL) {

    private val BUTTON_WIDTH = 400
    private var buttons: ArrayList<UnderlayButton>
    private var swipedPos = -1

    private var swipedThreshold = 10f
    private var listenerGestureDetector: GestureDetector.SimpleOnGestureListener
    private var gestureDetector: GestureDetector
    private lateinit var listenerOnTouch: View.OnTouchListener
    private var buttonsBuffer: MutableMap<Int, ArrayList<UnderlayButton>>
    private var recoverQueue: Queue<Int>

    init {
        buttons = ArrayList()
        buttonsBuffer = HashMap()
        recoverQueue = LinkedList<Int>()


        listenerGestureDetector = object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                for (button in buttons) {
                    if (button.onClick(e.x, e.y)) break
                }
                return true
            }
        }

        gestureDetector = GestureDetector(context, listenerGestureDetector)

        listenerOnTouch = View.OnTouchListener { v, event ->
            if (swipedPos < 0) return@OnTouchListener false

            val point = Point(event?.rawX!!.toInt(), event.rawY.toInt())
            val swipedViewHolder: RecyclerView.ViewHolder? =
                    recyclerView.findViewHolderForAdapterPosition(swipedPos)
            val rect = Rect()
            swipedViewHolder?.itemView?.getGlobalVisibleRect(rect)

            if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_MOVE) {
                if (rect.top < point.y ) {
                    gestureDetector.onTouchEvent(event)
                } else {
                    recoverQueue.add(swipedPos)
                    swipedPos = -1
                    recoverSwipedItem()
                }
            }
            false
        }


        attachSwipe();

        recyclerView.setOnTouchListener(listenerOnTouch)

    }

     private fun attachSwipe() {
        val itemTouchHelper = ItemTouchHelper(this)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onMove(recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition

        if (swipedPos != pos) {
            recoverQueue.add(swipedPos)
        }

        swipedPos = pos

        if (buttonsBuffer.containsKey(swipedPos)) {
            buttons = buttonsBuffer[swipedPos] ?: error("")
        } else {
            buttons.clear()
        }


        swipedThreshold = 5f * buttons.size * BUTTON_WIDTH
        recoverSwipedItem()
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return 0.1f * defaultValue
    }

    override fun onChildDraw(c: Canvas,
                             recyclerView: RecyclerView,
                             viewHolder: RecyclerView.ViewHolder,
                             dX: Float,
                             dY: Float,
                             actionState: Int,
                             isCurrentlyActive: Boolean) {
        val pos = viewHolder.adapterPosition
        var translationX = dX
        val itemView = viewHolder.itemView
        if (pos < 0) {
            swipedPos = pos
            return
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

            if (dX < 0) {
                var buffer: ArrayList<UnderlayButton> = ArrayList()
                if (!buttonsBuffer.containsKey(pos)) {
                    instantiateUnderlayButton(viewHolder, buffer)
                    buttonsBuffer.keys
                    buttonsBuffer[pos] = buffer
                } else {
                    buffer = buttonsBuffer[pos]!!
                }
                translationX = dX * buffer.size * BUTTON_WIDTH / itemView.width
                drawButtons(c, itemView, buffer, pos, translationX)


            }
        }
        super.onChildDraw(c,
                recyclerView,
                viewHolder,
                translationX,
                dY,
                actionState,
                isCurrentlyActive)
    }

    @Synchronized
    private fun recoverSwipedItem() {
        while (!recoverQueue.isEmpty()) {
            val pos: Int = recoverQueue.poll()
            if (pos > -1) {
                recyclerView.adapter!!.notifyItemChanged(pos)
            }
        }
    }

    private fun drawButtons(c: Canvas,
                            itemView: View,
                            buffer: List<UnderlayButton>,
                            pos: Int,
                            dX: Float) {
        var right = itemView.right.toFloat()
        val dButtonWidth = -1 * dX / buffer.size
        for (button in buffer) {
            val left = right - dButtonWidth
            button.onDraw(c,
                    RectF(left, itemView.top.toFloat(), right, itemView.bottom.toFloat()),
                    pos)
            right = left
        }
    }

    abstract fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder?,
                                           underlayButtons: ArrayList<UnderlayButton>)

    class UnderlayButton(private val text: String,
                         private val color: Int,
                         private val clickListener: UnderlayButtonClickListener) {
        private var pos = 0
        private var clickRegion: RectF? = null

        fun onClick(x: Float, y: Float): Boolean {
            if (clickRegion != null && clickRegion!!.contains(x, y)) {
                clickListener.onClick(pos)
                return true
            }
            return false
        }

        fun onDraw(c: Canvas, rect: RectF, pos: Int) {
            val p = Paint()
            // Draw background
            p.color = color
            c.drawRect(rect, p)
            // Draw Text
            p.color = Color.WHITE
            p.textSize = 54F
            val r = Rect()
            val cHeight: Float = rect.height()
            val cWidth: Float = rect.width()
            p.textAlign = Paint.Align.LEFT
            p.getTextBounds(text, 0, text.length, r)
            val x: Float = cWidth / 2f - r.width() / 2f - r.left
            val y: Float = cHeight / 2f + r.height() / 2f - r.bottom
            c.drawText(text, rect.left + x, rect.top + y, p)
            clickRegion = rect
            this.pos = pos
        }

    }

    interface UnderlayButtonClickListener {
        fun onClick(pos: Int)
    }

}
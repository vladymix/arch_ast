package com.ast.widgets

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.altamirano.fabricio.core.commons.CircleDraw

class PositionLayer(context: Context, attrs: AttributeSet?) : View(context, attrs),
    View.OnTouchListener {

    private var maxHeigh: Boolean = false
    private val circleDrawPoint: CircleDraw =
        CircleDraw(38f, 10f)

    init {
        // this.setOnTouchListener(this)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        try {
            circleDrawPoint.drawIn(canvas)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun setMaxHeight() {
        this.maxHeigh = true
    }

    fun setPostition(x: Float, y: Float) {
        this.circleDrawPoint.posY = y
        this.circleDrawPoint.posX = x
        this.circleDrawPoint.fixPosition(this.height, this.width)
        this.invalidate()
    }

    fun setPostition(x: Float, y: Float, inCircle: Boolean) {
        this.circleDrawPoint.posY = y
        this.circleDrawPoint.posX = x
        this.circleDrawPoint.fixPosition(this.height, this.width)

        if (inCircle) {
            this.circleDrawPoint.fixPositionCircle(this.width)
        }
        this.invalidate()
    }


    fun setColorFill(int: Int) {
        this.circleDrawPoint.setColorFill(int)
    }

    fun setColorStroke(int: Int) {
        this.circleDrawPoint.setColorStroke(int)
    }

    fun restore() {
        this.circleDrawPoint.posX = 0f
        this.circleDrawPoint.posY = 0f
        this.invalidate()
    }

    fun setEndPosition() {
        this.circleDrawPoint.radius = this.height.toFloat() / 2
        this.circleDrawPoint.posX = this.width.toFloat() - this.circleDrawPoint.radius
        this.circleDrawPoint.posY = this.circleDrawPoint.radius
        this.invalidate()
    }

    fun fillContent() {
        this.circleDrawPoint.radius = this.height.toFloat() / 2
        this.invalidate()
    }


    fun setStroke(fl: Float) {
        this.circleDrawPoint.stroke = fl
    }

    fun setPostitionScaled(x: Float, y: Float, sHeight: Int, sWidth: Int) {
        val scaleH = this.height / sHeight.toFloat()
        val scaleW = this.width / sWidth.toFloat()
        this.setPostition(x * scaleW, y * scaleH)

    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        var x = event!!.x
        var y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }

            MotionEvent.ACTION_MOVE -> {

            }
        }
        if (x < 0) {
            x = 0f
        }
        if (y < 0) {
            y = 0f
        }

        if (y > this.height) {
            y = this.height.toFloat()
        }
        if (x > this.width) {
            x = this.width.toFloat()
        }


        this.setPostition(x, y)
        return true
    }
}
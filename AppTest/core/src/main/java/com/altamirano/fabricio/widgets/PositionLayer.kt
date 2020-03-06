package com.altamirano.fabricio.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class PositionLayer(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var maxHeigh: Boolean = false

    val circle: Circle = Circle(Paint.Style.STROKE, 40f, 8f)
    val circleFill: Circle = Circle(Paint.Style.FILL, 38f, 0f)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        try {

            if (maxHeigh) {
                val div = (this.height / 2).toFloat()
                circle.posY = div
                circleFill.posY = div
                circle.radius = div-5
                circleFill.radius = div-5
            }

            canvas?.drawCircle(circle.posx, circle.posY, circle.radius, circle.paint)
            canvas?.drawCircle(
                    circleFill.posx,
                    circleFill.posY,
                    circleFill.radius,
                    circleFill.paint
            )

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun setMaxHeight() {
        this.maxHeigh = true
    }

    fun setPostition(x: Float, y: Float) {
        circle.posx = x
        circle.posY = y
        circleFill.posx = x
        circleFill.posY = y
        this.invalidate()
    }
    fun setPostition(x: Float, y: Float, targetHeigth:Int, targetWidth:Int) {

        val scale = this.height / targetHeigth.toFloat()
        circle.posx = x*scale
        circle.posY = y*scale
        circleFill.posx = x*scale
        circleFill.posY = y*scale

        fixCircleToContent()

        Log.i("TAG", "Position Scale: (X:${x*scale}, Y:${y*scale})  Position: (X:${x}, Y:${y}) height:${this.height}  Targetheight:${targetHeigth}")

        // this.invalidate()
    }

    fun setColorFill(int: Int) {
        circleFill.setColorFill(int)
    }

    fun restore() {
        circle.posx = 0f
        circle.posY = 0f
        circleFill.posx = 0f
        circleFill.posY = 0f
        this.invalidate()
    }
    fun setMaxWidth(){
        circle.posx = this.width.toFloat()
        circleFill.posx = this.width.toFloat()
        this.fixCircleToContent()
    }

    fun fixCircleToContent() {
        if(circle.posx < circle.radius){
            circle.posx = circle.radius
        }

        if(circle.posx > this.width - circle.radius){
            circle.posx = this.width - circle.radius
        }

        if(circleFill.posx < circleFill.radius){
            circleFill.posx = circleFill.radius
        }

        if(circleFill.posx > this.width - circleFill.radius){
            circleFill.posx = this.width - circleFill.radius
        }
        if(circle.posY < circle.radius){
            circle.posY = circle.radius
        }

        if(circle.posY > this.height - circle.radius){
            circle.posY = this.height - circle.radius
        }

        if(circleFill.posY < circleFill.radius){
            circleFill.posY = circleFill.radius
        }

        if(circleFill.posY > this.height - circle.radius){
            circleFill.posY = this.height - circle.radius
        }
        this.invalidate()
    }


    class Circle(style: Paint.Style = Paint.Style.STROKE, rad: Float = 30f, stroke: Float = 5F) {
        val paint: Paint = Paint()
        var posx: Float = rad-7 // To init center
        var posY: Float = rad // To init center
        var radius: Float = rad

        init {
            paint.color = Color.WHITE
            paint.strokeWidth = stroke
            paint.style = style
        }

        fun setColorFill(color: Int) {
            paint.color = color
            paint.style = Paint.Style.FILL
        }

        fun setColorStroke(color: Int) {
            paint.color = color
            paint.style = Paint.Style.STROKE
        }

    }
}
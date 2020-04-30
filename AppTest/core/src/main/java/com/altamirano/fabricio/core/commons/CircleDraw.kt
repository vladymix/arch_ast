package com.altamirano.fabricio.core.commons

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class CircleDraw (rad: Float = 30f, var stroke: Float = 10F) {
    private val paintStroke: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintFill: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var posX: Float = rad // To init center
    var posY: Float = rad // To init center

    var radius: Float = rad

    init {
        paintStroke.color = Color.WHITE
        paintStroke.style = Paint.Style.FILL

        paintFill.color = Color.BLACK
        paintFill.style = Paint.Style.FILL
    }

    fun setColorFill(color: Int) {
        paintFill.color = color
    }

    fun setColorStroke(color: Int) {
        paintStroke.color = color
    }

    fun fixPosition(height: Int, width: Int) {
        if (posX < 0) {
            posX = 0f
        }
        if (posY < 0) {
            posY = 0f
        }
        if (posY > height) {
            posY = height.toFloat()
        }
        if (posX > width) {
            posX = width.toFloat()
        }

        if (posX > width - radius) {
            posX = width - radius
        }

        if (posY > height - radius) {
            posY = height - radius
        }
        if (posX < radius) {
            posX = radius
        }
        if (posY < radius) {
            posY = radius
        }
    }

    fun fixPositionCircle(radiusOrigin: Int) {
        val radiusTarget = (radiusOrigin - (radius * 2)) / 2
        val point = PointCicle(posX, posY)
        point.fixWith(radiusTarget, radiusOrigin / 2)
        posY = point.y
        posX = point.x
    }

    fun drawIn(canvas: Canvas?) {
        canvas?.drawCircle(
            this.posX,
            this.posY,
            this.radius,
            this.paintStroke
        )

        if (this.stroke > 0) {
            canvas?.drawCircle(
                this.posX,
                this.posY,
                this.radius - stroke,
                this.paintFill
            )
        }
    }
}


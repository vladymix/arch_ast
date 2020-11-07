package com.ast.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.altamirano.fabricio.core.R
import com.altamirano.fabricio.core.commons.ColorPicker
import com.altamirano.fabricio.core.commons.PointF
import kotlin.math.pow

class ColorCircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val mRealImage: Bitmap = Bitmap.createScaledBitmap(
        BitmapFactory.decodeResource(
            resources,
            R.drawable.ast_gamma_colors
        ), 350, 350, false
    )
    private val mPositionColor = PointF(0f, 0f)
    private val mPointWhitePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPointColor = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRadiusColor = 40f
    private var radiusImage = 0f
    private var colorSelected = Color.WHITE
    private var destinationImage = Rect()
    private var mImageGameScaled: Bitmap? = null
    private var colorOld = 0
    private var point: PointF? = null
    var onColorChangeListener: ((ColorPicker?) -> Unit)? = null

    init {
        mPositionColor.radius = mRadiusColor
        mPointWhitePaint.strokeWidth = 0f
        mPointWhitePaint.style = Paint.Style.FILL
        mPointWhitePaint.color = Color.WHITE

        mPointColor.strokeWidth = 0f
        mPointColor.style = Paint.Style.FILL
        mPointColor.color = Color.WHITE
    }

    override fun onDraw(canvas: Canvas?) {
        radiusImage = width / 2 - mRadiusColor
        canvas?.let {
            drawImage(it)
            drawPoint(it)
        }
        super.onDraw(canvas)
    }

    private fun drawImage(canvas: Canvas) {
        val min = Math.min(height, width)

        if (mImageGameScaled == null) {
            mImageGameScaled = Bitmap.createScaledBitmap(
                mRealImage,
                min - 2 * mRadiusColor.toInt(),
                min - 2 * mRadiusColor.toInt(),
                false
            )
        }

        destinationImage.set(
            mRadiusColor.toInt(),
            mRadiusColor.toInt(),
            min - mRadiusColor.toInt(),
            min - mRadiusColor.toInt()
        )

        mImageGameScaled?.let {
            canvas.drawBitmap(it, null, destinationImage, null)
        }
    }

    private fun isInImage(x: Float, y: Float): Boolean {

        val h = width / 2
        val k = height / 2

        val xpos = (x - h).toDouble().pow(2)
        val ypos = (y - k).toDouble().pow(2)
        val rPos = radiusImage.toDouble().pow(2)
        return xpos + ypos <= rPos
    }

    private fun fixPosCircle(x: Float, y: Float) {

        val h = width / 2
        val k = height / 2
        val rPos = (radiusImage).toDouble().pow(2)

        var xpos = (x - h).toDouble().pow(2)
        var ypos = (y - k).toDouble().pow(2)

        mPositionColor.fixPosition(x, y, width, height)
        var fx = mPositionColor.x
        var fy = mPositionColor.y

        var noInCircle = xpos + ypos > rPos

        while (noInCircle) {
            when (this.getCuadrante(x, y)) {
                //  1  |  2
                // --------
                //  3  |  4
                1 -> {
                    fx++
                    fy++
                }
                2 -> {
                    fx--
                    fy++
                }
                3 -> {
                    fx++
                    fy--

                }
                4 -> {
                    fx--
                    fy--
                }
            }

            xpos = (fx - h).toDouble().pow(2)
            ypos = (fy - k).toDouble().pow(2)
            noInCircle = xpos + ypos > rPos
        }


        this.drawPointIn(fx, fy)
    }

    private fun getCuadrante(x: Float, y: Float): Int {

        if (x < width / 2 && y < height / 2) {
            return 1
        } else if (x > width / 2 && y < width / 2) {
            return 2
        } else if (y > height / 2 && x < width / 2) {
            return 3
        } else {
            return 4
        }
    }

    private fun drawPoint(canvas: Canvas) {
        if (mPositionColor.x == 0f && mPositionColor.y == 0f) {
            mPositionColor.x = (height / 2).toFloat()
            mPositionColor.y = (width / 2).toFloat()
        }

        if (pendingLoadStarColor) {
            this.searchColor(colorSelected)
            pendingLoadStarColor = false
        }

        colorSelected =
            this.getPixel(mRealImage, destinationImage, mPositionColor.x, mPositionColor.y)

        if (colorOld != colorSelected) {
            colorOld = colorSelected
            val red = Color.red(colorSelected)
            val green = Color.green(colorSelected)
            val blue = Color.blue(colorSelected)
            onColorChangeListener?.invoke(ColorPicker(255, red, green, blue))

        }

        canvas.drawCircle(mPositionColor.x, mPositionColor.y, mRadiusColor, mPointWhitePaint)

        mPointColor.color = colorSelected
        canvas.drawCircle(mPositionColor.x, mPositionColor.y, mRadiusColor - 7, mPointColor)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val size = if (width > height) height else width
        this.setMeasuredDimension(size, size)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN ->{
                    if (this.isInImage(it.x, it.y)) {
                        this.drawPointIn(it.x, it.y)
                    } else {
                        this.fixPosCircle(it.x, it.y)
                    }
                    return true
                }
                else ->{
                    return false
                }
            }
        }
        return false
    }

    private fun drawPointIn(x: Float, y: Float) {
        mPositionColor.fixPosition(x, y, width, height)
        this.invalidate()
    }

    fun setStartColor(colorPicker: ColorPicker?) {
        colorPicker?.let {
            this.colorSelected = it.getAsColor()
            if (this.height == 0) {
                pendingLoadStarColor = true
            } else {
                this.colorSelected = it.getAsColor()
                this.searchColor(it.getAsColor())
            }

        }
    }

    var pendingLoadStarColor = false

    private fun searchColor(colorPicker: Int) {
        val start = System.currentTimeMillis()
        mRealImage.let {

            point = null
            val c1 = Thread {
                point = findByCuadrantes(it, 0, 0, colorPicker, 1)
            }

            val c2 = Thread {
                point = findByCuadrantes(it, it.width / 2, 0, colorPicker, 2)
            }

            val c3 = Thread {
                point = findByCuadrantes(it, 0, it.height / 2, colorPicker, 3)
            }

            val c4 = Thread {
                point = findByCuadrantes(it, it.width / 2, it.height / 2, colorPicker, 4)
            }

            c1.start()
            c2.start()
            c3.start()
            c4.start()

            if (point == null) {
                // TODO
                c1.join()
                c2.join()
                c3.join()
                c4.join()
            }

            point?.let { pt ->
                val scale = destinationImage.height().toFloat() / it.height

                val x = scale * pt.x
                val y = scale * pt.y

                mPositionColor.x = x + destinationImage.left
                mPositionColor.y = y + destinationImage.top

                this.invalidate()
            }
        }
    }


    private fun findByCuadrantes(
        bitmap: Bitmap,
        startX: Int,
        startY: Int,
        pixel: Int,
        cuadrante: Int
    ): PointF? {
        var x = startX
        var y = startY

        when (cuadrante) {
            1 -> {
                while (x < bitmap.width / 2 && point == null) {
                    while (y < bitmap.height / 2 && point == null) {
                        val pixelIz = bitmap.getPixel(x, y)
                        if (pixelIz == pixel) {
                            return PointF(x.toFloat(), y.toFloat())
                        }
                        y++
                    }
                    y = startY
                    x++
                }
            }
            2 -> {
                while (x < bitmap.width && point == null) {
                    while (y < bitmap.height / 2 && point == null) {
                        val pixelIz = bitmap.getPixel(x, y)
                        if (pixelIz == pixel) {
                            return PointF(x.toFloat(), y.toFloat())
                        }
                        y++
                    }
                    y = startY
                    x++
                }
            }
            3 -> {
                while (x < bitmap.width / 2 && point == null) {
                    while (y < bitmap.height && point == null) {
                        val pixelIz = bitmap.getPixel(x, y)
                        if (pixelIz == pixel) {
                            return PointF(x.toFloat(), y.toFloat())
                        }

                        y++
                    }
                    y = startY
                    x++
                }
            }
            4 -> {
                while (x < bitmap.width && point == null) {
                    while (y < bitmap.height && point == null) {
                        val pixelIz = bitmap.getPixel(x, y)
                        if (pixelIz == pixel) {
                            return PointF(x.toFloat(), y.toFloat())
                        }
                        y++
                    }
                    y = startY
                    x++
                }
            }
        }
        return point
    }

    private fun getPixel(bmp: Bitmap, destinationRect: Rect, xp: Float, yp: Float): Int {

        val scaleH = bmp.height.toFloat() / destinationRect.height()
        val scaleW = bmp.width.toFloat() / destinationRect.width()
        var x = (scaleW * (xp - destinationRect.left)).toInt()
        var y = (scaleH * (yp - destinationRect.top)).toInt()


        if (x <= 0) {
            x = 1
        }
        if (x >= bmp.width) {
            x = bmp.width - 1
        }

        if (y <= 0) {
            y = 1
        }

        if (y >= bmp.height) {
            y = bmp.height - 1
        }


        return bmp.getPixel(x, y)
    }
}
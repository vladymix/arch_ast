package com.altamirano.fabricio.core.tools

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import java.io.ByteArrayOutputStream

/**
 * @autor Created by Fabricio Altamirano on 7/3/18.
 */
object ImageTools {
    /**
     * @param res resource app
     * @param source input image any size
     * @return Bitmap s circle image
     */
    fun getCircleBitmap(res: Resources?, source: Bitmap?): Bitmap? {

        // Create bitmap Squeare to create perfect circle image
        val square = getSquareBitmap(source)
        return getRoundedCornerBitmapDrawable(res, square, square!!.height.toFloat()).bitmap
    }

    /**
     * @param source Drawable image
     * @return Bitmap circle
     */
    fun getCircleBitmap(source: Drawable): Bitmap {
        return getCircleBitmap((source as BitmapDrawable).bitmap)
    }

    /**
     * @param source Bitmap
     * @return Bitmap circle
     */
    fun getCircleBitmap(source: Bitmap?): Bitmap {
        val width: Int
        val height: Int
        val bitmap = getSquareBitmap(source)
        val minSize = Math.min(bitmap!!.width, bitmap.height)
        height = minSize
        width = height
        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, width, height)
        val rectF = RectF(rect)
        val roundPx = minSize.toFloat()
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    /**
     * @param res resource app
     * @param source input image any size
     * @return RoundedBitmapDrawable as circle image
     */
    fun getCircleBitmapDrawable(res: Resources?, source: Bitmap?): RoundedBitmapDrawable {

        // Create bitmap Squeare to create perfect circle image
        val square = getSquareBitmap(source)
        return getRoundedCornerBitmapDrawable(res, square, square!!.height.toFloat())
    }

    /**
     * @param res resource app
     * @param source input image any size
     * @param cornerRadius size to corner radius image
     * @return RoundedBitmapDrawable with corner radius
     */
    fun getRoundedCornerBitmapDrawable(
        res: Resources?,
        source: Bitmap?,
        cornerRadius: Float
    ): RoundedBitmapDrawable {

        // Create RoundedBitmapDrawable
        val roundedDrawable = RoundedBitmapDrawableFactory.create(res!!, source)

        // Set cornerRadius
        roundedDrawable.cornerRadius = cornerRadius
        return roundedDrawable
    }

    /**
     * @param drawable input any drawable
     * @param cornerRadius set radius corner to image
     * @return bitmap with rounded corners
     */
    fun getRoundedCornerBitmap(drawable: Drawable, cornerRadius: Float): Bitmap {
        val bitmap = (drawable as BitmapDrawable).bitmap
        return getRoundedCornerBitmap(bitmap, cornerRadius)
    }

    /**
     * @param bitmap  input any bitmap
     * @param cornerRadius et radius corner to image
     * @return bitmap with rounded corners
     */
    fun getRoundedCornerBitmap(bitmap: Bitmap, cornerRadius: Float): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, width, height)
        val rectF = RectF(rect)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    /**
     * @param bitmap input image any size
     * @return bitmap with min size as square
     */
    fun getSquareBitmap(bitmap: Bitmap?): Bitmap? {
        return if (bitmap != null) {
            if (bitmap.height >= bitmap.width) {
                Bitmap.createBitmap(
                    bitmap,
                    0,
                    bitmap.height / 2 - bitmap.width / 2,
                    bitmap.width,
                    bitmap.width
                )
            } else {
                Bitmap.createBitmap(
                    bitmap,
                    bitmap.width / 2 - bitmap.height / 2,
                    0,
                    bitmap.height,
                    bitmap.height
                )
            }
        } else {
            null
        }
    }

    /**
     * @param drawable input any png file as drawable
     * @param color send color with Color library as Color.Red
     * @return Drawable with only color
     */
    fun getDrawableChangedColor(drawable: Drawable?, color: Int): Drawable {
        val wrappedDrawable = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTint(wrappedDrawable, color)
        return wrappedDrawable
    }

    /**
     * @param image input Image Bitmap
     * @return code Bitmap as String Base64
     */
    fun encodeBitmapToBase64(image: Bitmap): String {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 90, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    /**
     * @param inputBase64 String Base64
     * @return Bitmap image
     */
    fun decodeBase64ToBitmap(inputBase64: String?): Bitmap {
        val decodedByte = Base64.decode(inputBase64, 0)
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
    }
}
package com.altamirano.fabricio.libraryast;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

/**
 * @autor Created by Fabricio Altamirano on 7/3/18.
 */

public class ImageTools {

    /**
     * @param res resource app
     * @param source input image any size
     * @return Bitmap s circle image
     */
    public static Bitmap getCircleBitmap(Resources res, Bitmap source) {

        // Create bitmap Squeare to create perfect circle image
        Bitmap square = getSquareBitmap(source);

        return getRoundedCornerBitmapDrawable(res, square, square.getHeight()).getBitmap();
    }

    public static Bitmap getCircleBitmap(Bitmap source) {
        int width = 0;
        int height = 0;

        Bitmap bitmap = getSquareBitmap(source);
        int minSize = Math.min(bitmap.getWidth(),bitmap.getHeight());

        width = height = minSize;


        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);
        final float roundPx = minSize;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * @param res resource app
     * @param source input image any size
     * @return RoundedBitmapDrawable as circle image
     */
    public static RoundedBitmapDrawable getCircleBitmapDrawable(Resources res, Bitmap source) {

        // Create bitmap Squeare to create perfect circle image
        Bitmap square = getSquareBitmap(source);

        return getRoundedCornerBitmapDrawable(res, square, square.getHeight());
    }

    /**
     * @param res resource app
     * @param source input image any size
     * @param cornerRadius size to corner radius image
     * @return RoundedBitmapDrawable with corner radius
     */
    public static RoundedBitmapDrawable getRoundedCornerBitmapDrawable(Resources res, Bitmap source, float cornerRadius) {

        // Create RoundedBitmapDrawable
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(res, source);

        // Set cornerRadius
        roundedDrawable.setCornerRadius(cornerRadius);

        return roundedDrawable;
    }

    /**
     * @param drawable input any drawable
     * @param cornerRadius set radius corner to image
     * @return bitmap with rounded corners
     */
    public static Bitmap getRoundedCornerBitmap(Drawable drawable, float cornerRadius){
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap() ;

        return getRoundedCornerBitmap(bitmap, cornerRadius);

    }

    /**
     * @param bitmap  input any bitmap
     * @param cornerRadius et radius corner to image
     * @return bitmap with rounded corners
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float cornerRadius) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * @param bitmap input image any size
     * @return bitmap with min size as square
     */
    public static Bitmap getSquareBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            if (bitmap.getHeight() >= bitmap.getWidth()) {
                return Bitmap.createBitmap(
                        bitmap,
                        0,
                        bitmap.getHeight() / 2 - bitmap.getWidth() / 2,
                        bitmap.getWidth(),
                        bitmap.getWidth());
            } else {
                return Bitmap.createBitmap(
                        bitmap,
                        bitmap.getWidth() / 2 - bitmap.getHeight() / 2,
                        0,
                        bitmap.getHeight(),
                        bitmap.getHeight());
            }
        } else {
            return null;
        }
    }




}

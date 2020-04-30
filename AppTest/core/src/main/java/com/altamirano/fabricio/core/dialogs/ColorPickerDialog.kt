package com.altamirano.fabricio.core.dialogs

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altamirano.fabricio.core.adapters.ColorTempAdapter
import com.altamirano.fabricio.core.R
import com.altamirano.fabricio.core.tools.asyncTask
import com.altamirano.fabricio.core.commons.PointCicle
import com.ast.widgets.PositionLayer

@SuppressLint("ValidFragment")
class ColorPickerDialog(var listener: ((ColorPicker?) -> Unit)? = null) : DialogFragment(),
    View.OnTouchListener {

    private var FILE_CACHE = "picker_color_cache"
    private val LIST_CACHE = "temp_colors"

    private lateinit var listTemp: ArrayList<ColorPicker>
    private var bitmap: Bitmap? = null

    var colorInit: ColorPicker? = ColorPicker(255, 255, 255, 255)
    private var colorSelected: ColorPicker = ColorPicker(0, 0, 0, 0)

    private lateinit var colortext: TextView
    private lateinit var viewGradient: ImageView
    private lateinit var positionLayer: PositionLayer
    private lateinit var positionLayerGradient: PositionLayer
    private lateinit var recicleView: RecyclerView
    private lateinit var imagePicker: ImageView
    private lateinit var progressIndicator: ProgressBar
    private lateinit var viewTarget: View



    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.ast_layout_color_dialog, container)

        this.imagePicker = view.findViewById(R.id.imagePicker)
        this.progressIndicator = view.findViewById(R.id.progressIndicator)
        this.viewTarget = view.findViewById(R.id.viewTarget)
        val viewCurrent = view.findViewById<View>(R.id.viewCurrent)

        this.positionLayer = view.findViewById(R.id.positionLayer)
        this.recicleView = view.findViewById(R.id.recicleView)
        this.positionLayerGradient = view.findViewById(R.id.positionLayerGradient)

        this.positionLayerGradient.setMaxHeight()
        this.positionLayerGradient.setStroke(5f)

        view.findViewById<View>(R.id.btnAcept).setOnClickListener { this.onAccept() }
        view.findViewById<View>(R.id.btnCancel).setOnClickListener { this.dismiss() }

        this.colortext = view.findViewById(R.id.colortext)
        this.viewGradient = view.findViewById(R.id.viewGradient)

        this.imagePicker.setOnTouchListener(this)

        this.viewGradient.setOnTouchListener { v, event -> this.onTouchContrast(v, event) }


        colorInit?.let {
            this.positionLayerGradient.setColorFill(it.getAsColor())
            this.createGradientToColor(it)
            viewCurrent.setBackgroundColor(it.getAsColor())
            this.positionLayerGradient.setEndPosition()

        }

        recicleView.layoutManager = GridLayoutManager(this.context, 5)

        this.listTemp = ArrayList(this.context!!.getCacheColor())

        this.context?.let {
            recicleView.adapter =
                ColorTempAdapter(it.getCacheColor()) { this.onItemColorSelected(it) }
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return view
    }

    override fun show(manager: FragmentManager, tag: String?) {
        tag?.let {
            this.FILE_CACHE = it
        }

        super.show(manager, tag)
    }

    private fun setValue(colorPicker: ColorPicker?) {
        colorPicker?.let {
            colorSelected = it
            viewTarget.setBackgroundColor(it.getAsColor())
            colorSelected.run {
                colortext.setText("R:$red G:$green B:$blue")
            }
        }
    }

    private fun onItemColorSelected(colorPicker: ColorPicker?) {
        this.setValue(colorPicker)
        colorPicker?.let {
            serachAsyncColor(it)
        }
    }

    private fun serachAsyncColor(colorPicker: ColorPicker) {
        asyncTask(
            preExecute = {
                progressIndicator.visibility = View.VISIBLE
            },
            doInBackground = {
                findColor(colorPicker)
            },
            postExecute = {
                progressIndicator.visibility = View.GONE
                it?.let { point ->
                    bitmap?.let {
                        try {
                            val scale = bitmap!!.height / imagePicker.height.toFloat()
                            this.positionLayer.setColorFill(colorSelected.getAsColor())
                            this.positionLayer.setPostition(
                                point.x / scale,
                                point.y / scale, true
                            )
                            this.positionLayerGradient.setColorFill(colorSelected.getAsColor())
                            this.createGradientToColor(colorSelected)
                            this.positionLayerGradient.setEndPosition()

                        } catch (ex: java.lang.Exception) {
                            ex.printStackTrace()
                        }
                    }
                }
            }
        )
    }


    fun findColor(colorPicker: ColorPicker): Point? {
        var px = 0
        var py = 0
        val bitmap = getBitmap(this.imagePicker)

        while (px < bitmap.width) {
            while (py < bitmap.height) {
                val pixel = bitmap.getPixel(px, py)
                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                if (ColorPicker(colorPicker.alfa, red, green, blue).equals(colorPicker)) {
                    return Point(px, py)
                }
                py++
            }
            py = 0

            px++
        }
        return null
    }

    private fun getBitmap(view: View): Bitmap {
        if (bitmap == null) {
            bitmap = ((view as ImageView).drawable as BitmapDrawable).bitmap
        }
        return this.bitmap!!
    }

    private fun onTouchContrast(v: View?, event: MotionEvent?): Boolean {

        var x = event!!.x
        val y: Float

        when (event.action) {
            MotionEvent.ACTION_DOWN -> Log.i("TAG", "touched down")
            MotionEvent.ACTION_MOVE -> {

                try {

                    if (x < 0) {
                        x = 0f
                    }

                    val imageView = v as ImageView

                    y = imageView.height / 2.toFloat()

                    if (x > imageView.width) {
                        x = imageView.width - 1.toFloat()
                    }

                    val bitmap = loadBitmapFromView(imageView)
                    val scale = bitmap.height.toFloat() / imageView.height.toFloat()


                    Log.i("TAG", "pixel: (X${(x * scale).toInt()}, y${(y * scale).toInt()})")
                    val argx = (x * scale).toInt()
                    val argy = (y * scale).toInt()

                    Log.i("TAG", "pixel scale: (X${argx}, y${argy})")
                    Log.i("TAG", "pixel bitma: (X${bitmap.width}, y${bitmap.height})")
                    if (argx > bitmap.width || argy >= bitmap.height) {
                        Log.i("TAG", "pixel scale:false")
                        return false
                    }

                    Log.i("TAG", "pixel scale:continue")
                    val pixel = bitmap.getPixel(argx, argy)
                    val red = Color.red(pixel)
                    val green = Color.green(pixel)
                    val blue = Color.blue(pixel)

                    Log.i("TAG", "moving: ($x, $y)")

                    this.colorSelected = ColorPicker(255, red, green, blue)
                    this.positionLayerGradient.setColorFill(colorSelected.getAsColor())
                    this.positionLayerGradient.fillContent()
                    this.positionLayerGradient.setPostition(x, y)

                    this.setValue(colorSelected)

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }
            MotionEvent.ACTION_UP -> Log.i("TAG", "touched up")
        }

        return true
    }

    private fun onAccept() {
        val exist = this.listTemp.find { it.equals(this.colorSelected) }

        if (exist != null) {
            this.listTemp.remove(exist)
        }

        this.listTemp.add(0, this.colorSelected)

        this.context?.saveCacheColor(this.listTemp)
        listener?.invoke(this.colorSelected)
        this.dismiss()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        var x = event!!.x
        var y = event.y


        when (event.action) {
            MotionEvent.ACTION_DOWN -> Log.i("TAG", "touched down")
            MotionEvent.ACTION_MOVE -> {
                try {
                    val imageView = v as ImageView

                    if (x < 0) {
                        x = 0f
                    }
                    if (x > imageView.width) {
                        x = imageView.width.toFloat()
                    }

                    if (y < 0) {
                        y = 0f
                    }

                    if (y > imageView.height) {
                        y = imageView.height.toFloat()
                    }


                    val point = PointCicle(x, y)
                    point.fixWith(imageView.height / 2.toFloat(), imageView.height / 2)

                    val pointLayer = PointCicle(x, y)
                    pointLayer.fixWith(imageView.height / 2.toFloat() - 38f, imageView.height / 2)


                    val bitmap = this.getBitmap(v)
                    val scale = bitmap.height / imageView.height.toFloat()

                    val pointX = (point.x * scale).toInt()
                    val pointY = (point.y * scale).toInt()

                    val pixel = bitmap.getPixel(pointX, pointY)
                    val red = Color.red(pixel)
                    val green = Color.green(pixel)
                    val blue = Color.blue(pixel)

                    this.colorSelected = ColorPicker(255, red, green, blue)
                    this.positionLayer.setColorFill(colorSelected.getAsColor())

                    this.positionLayer.setPostitionScaled(
                        pointLayer.x,
                        pointLayer.y,
                        imageView.height,
                        imageView.width
                    )


                    this.positionLayerGradient.setColorFill(colorSelected.getAsColor())
                    this.positionLayerGradient.setEndPosition()

                    this.createGradientToColor(colorSelected);

                    this.setValue(colorSelected)

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }
            MotionEvent.ACTION_UP -> Log.i("TAG", "touched up")
        }

        return true
    }


    private fun createGradientToColor(color: ColorPicker) {
        val gp = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(Color.BLACK, color.getAsColor())
        )
        gp.cornerRadius = 50f
        this.viewGradient.setBackgroundDrawable(gp)
    }

    fun loadBitmapFromView(v: View): Bitmap {
        val b = Bitmap.createBitmap(
            v.layoutParams.width,
            v.layoutParams.height,
            Bitmap.Config.ARGB_8888
        )
        val c = Canvas(b)
        v.layout(v.left, v.top, v.right, v.bottom)
        v.draw(c)
        return b
    }

    class ColorPicker(var alfa: Int, var red: Int, var green: Int, var blue: Int) {

        fun getAsColor(): Int {
            return Color.argb(alfa, red, green, blue)
        }

        fun getColor(percentage: Float): Int {
            return Color.argb((alfa * percentage).toInt(), red, green, blue)
        }

        override fun equals(other: Any?): Boolean {
            val target = (other as ColorPicker)

            return target.alfa == this.alfa && target.red == this.red && target.green == this.green
                    && target.blue == this.blue
        }

        override fun hashCode(): Int {
            var result = alfa
            result = 31 * result + red
            result = 31 * result + green
            result = 31 * result + blue
            return result
        }
    }

    companion object {
        fun getDefaultColor(): ColorPicker {
            return ColorPicker(255, 48, 192, 175)
        }
    }

    fun Context.getCacheColor(): List<ColorPicker> {
        val colors = ArrayList<ColorPicker>()
        val sharedPref = this.getSharedPreferences(FILE_CACHE, Context.MODE_PRIVATE)
        val colorsString = sharedPref.getString(LIST_CACHE, "")!!

        val splitList = colorsString.split("%")

        for (item in splitList) {
            if (item.isNotEmpty()) {
                colors.add(colorToRestoreString(item))
            }
        }
        return colors;
    }

    fun Context.saveCacheColor(items: List<ColorPicker>) {
        val sharedEdit =
            this.getSharedPreferences(FILE_CACHE, Context.MODE_PRIVATE).edit()

        val strn = StringBuilder()

        for (item in items) {
            strn.append(colorPickerToStoredString(item)).append("%")
        }
        sharedEdit.putString(LIST_CACHE, strn.toString()).apply()

    }

    fun colorToRestoreString(value: String?): ColorPicker {

        if (value.isNullOrEmpty()) {
            return getDefaultColor()
        }
        val array = value.split("|")

        return ColorPicker(array[0].toInt(), array[1].toInt(), array[2].toInt(), array[3].toInt())
    }


    fun colorPickerToStoredString(color: ColorPicker): String {
        return "${color.alfa}|${color.red}|${color.green}|${color.blue}"
    }


}
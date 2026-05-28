package com.altamirano.fabricio.core.commons

import android.util.Log

data class PointCicle (var x: Float, var y: Float) {

    fun fixWith(radio: Float, center: Int) {
        if (x > radio && y < radio) {// Primer cuadrante
            val catOP = x-center
            val catAdy = center-y

            val radioT = this.getRadio(catOP, catAdy)
            if(radioT<= radio){
                return
            }

            val angle  =Math.acos(catOP/radioT)

            val xC = center + radio*Math.cos(angle)
            val yC = center - radio*Math.sin(angle)

            Log.i("Angle 1",angle.toString())
            x= xC.toFloat()
            y = yC.toFloat()

        } else if (x < radio && y < radio) {// Segundo cuadrante
            val catOP = center-x
            val catAdy =center-y

            val radioT = this.getRadio(catOP, catAdy)
            if(radioT<= radio){
                return
            }

            val angle  =Math.acos(catOP/radioT)

            val xC = center - radio*Math.cos(angle)
            val yC = center - radio*Math.sin(angle)

            Log.i("Angle 2",angle.toString())

            x= xC.toFloat()
            y = yC.toFloat()

        } else if (x < radio && y > radio) {// Tercer cuadrante

            val catOP =  center-x
            val catAdy =y-center

            val radioT = this.getRadio(catOP, catAdy)
            if(radioT<= radio){
                return
            }
            val angle  =Math.acos(catOP/radioT)

            val xC = center - radio*Math.cos(angle)
            val yC = center + radio*Math.sin(angle)
            Log.i("Angle 3",angle.toString())

            x= xC.toFloat()
            y = yC.toFloat()


        } else if (x > radio && y > radio) { // Cuarto cuadrante
            val catOP = x - center
            val catAdy = center-y

            val radioT = this.getRadio(catOP, catAdy)
            if(radioT<= radio){
                return
            }
            val angle  =Math.acos(catOP/radioT)

            val xC = center + radio*Math.cos(angle)
            val yC = center + radio*Math.sin(angle)
            Log.i("Angle 4",angle.toString())

            x= xC.toFloat()
            y = yC.toFloat()
        }
    }

    private fun getRadio(catOp:Float, capAd:Float):Double{
        return Math.sqrt(((catOp*catOp)+(capAd*capAd)).toDouble())
    }
}
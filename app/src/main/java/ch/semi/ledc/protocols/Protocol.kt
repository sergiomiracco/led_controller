package ch.semi.ledc.protocols

import android.graphics.Color

interface Protocol{

    fun getInstance() : Protocol

    fun enableFunction(function: Functions)

    fun disableFunction(function: Functions)

    fun setColor(color: Color)

    fun dark()

}
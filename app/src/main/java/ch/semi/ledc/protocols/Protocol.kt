package ch.semi.ledc.protocols

import android.graphics.Color
import ch.semi.ledc.controllers.Functions

interface Protocol{

    fun enableFunction(function: Functions)

    fun disableFunction(function: Functions)

    fun setColor(color: Color)

    fun dark()

}
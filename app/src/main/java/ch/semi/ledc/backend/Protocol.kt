package ch.semi.ledc.backend

import android.graphics.Color
import ch.semi.ledc.backend.Functions
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

class Protocol {

    private var sRed : Byte = 0
    private var sGreen : Byte = 0
    private var sBlue : Byte = 0

    private var sFunctionByte : Byte = 0b00000000

    private val sProtocol : ByteArray = ByteArray(4)

    public fun enableFunction(function: Functions){
        sFunctionByte = sFunctionByte or function.getBytePattern()
    }

    public fun disableFunction(function: Functions){
        sFunctionByte = sFunctionByte and function.getBytePattern().inv()
    }

    public fun setColor(color: Long){
        TODO("not yet implemented")
    }

    public fun setColor(red: Byte, green: Byte, blue: Byte){

    }

    public fun switchOff(){
        disableFunction(Functions.ON_OFF)

    }
}
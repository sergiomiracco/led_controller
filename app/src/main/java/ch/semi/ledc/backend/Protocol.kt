package ch.semi.ledc.backend

import android.graphics.Color
import ch.semi.ledc.backend.Functions
import java.lang.StringBuilder
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

class Protocol {

    private var sRed : Byte = 0
    private var sGreen : Byte = 0
    private var sBlue : Byte = 0

    private var sFunctionByte : Byte = 0b00000000

    private val sMessage : ByteArray = ByteArray(4)

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
        sRed=red
        sGreen=green
        sBlue=blue

    }

    public fun switchOff(){
        disableFunction(Functions.ON_OFF)

    }

    public fun getMessage(): ByteArray {
        sMessage[0] = sFunctionByte
        sMessage[1] = sRed
        sMessage[2] = sGreen
        sMessage[3] = sBlue

        return sMessage

    }

    public override fun toString(): String {

        val sb = StringBuilder()

        for(i in 0..7){
            if( !((sMessage[0] and Functions.fromIndex(i.toByte())!!.getBytePattern()).equals(0)) ){
                sb.append(Functions.fromIndex(i.toByte())?.name)
            }
        }

        val colorNames = Array(4){"", "red: ", "green: ", "blue: "}

        for(i in 1..3){
            sb.append(colorNames[i])
            val num = sMessage[i]
            sb.append(num.toString())

        }

        return sb.toString()

    }

}
package ch.semi.ledc.protocols

import android.graphics.Color
import ch.semi.ledc.controllers.Functions
import java.lang.StringBuilder
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

class My4ByteProtocol : Protocol {

    private var sRed : Byte = 0
    private var sGreen : Byte = 0
    private var sBlue : Byte = 0

    private var sFunctionByte : Byte = 0b00000000

    private val sMessage : ByteArray = ByteArray(4)


    override fun enableFunction(function: Functions) {
        sFunctionByte = sFunctionByte or function.getBytePattern()
    }

    override fun disableFunction(function: Functions) {
        sFunctionByte = sFunctionByte and function.getBytePattern().inv()
    }

    override fun setColor(color: Color) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dark() {
        disableFunction(Functions.ON_OFF)
    }

    public fun setColor(red: Byte, green: Byte, blue: Byte){
        sRed=red
        sGreen=green
        sBlue=blue

    }


    public fun getMessage(): ByteArray {
        sMessage[0] = sFunctionByte
        sMessage[1] = sRed
        sMessage[2] = sGreen
        sMessage[3] = sBlue

        return sMessage

    }

    override fun toString(): String {

        val sb = StringBuilder()

        for(i in 0..7){
            if( !((sMessage[0] and Functions.fromIndex(i.toByte())!!.getBytePattern()).equals(0)) ){
                sb.append(Functions.fromIndex(i.toByte())?.name)
            }
        }

        val colorNames = arrayOf("red: ", "green: ", "blue: ")

        for(i in 1..3){
            sb.append(colorNames[i-1])
            val num = sMessage[i]
            sb.append(num.toString())
        }

        return sb.toString()

    }

}
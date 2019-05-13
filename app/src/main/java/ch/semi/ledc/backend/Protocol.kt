package ch.semi.ledc.backend

import android.graphics.Color
import ch.semi.ledc.backend.Functions
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

class Protocol {

    private var sColor : Color = Color()
    private var sFunctionByte : Byte = 0b00000000

    private val sProtocol : ByteArray = ByteArray(4)

    public fun enableFunction(function: Functions){
        sProtocol[0] = sProtocol[0] or function.getBytePattern()
    }

    public fun disableFunction(function: Functions){
        sProtocol[0] = sProtocol[0] and function.getBytePattern().inv()
    }


}
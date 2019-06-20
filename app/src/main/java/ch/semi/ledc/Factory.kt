package ch.semi.ledc

import ch.semi.ledc.controllers.Backend
import ch.semi.ledc.controllers.BluetoothSerial
import ch.semi.ledc.protocols.My4ByteProtocol
import ch.semi.ledc.protocols.Protocol

class Factory {

    val drivers = mapOf(
        "RN42-CDB6" to BluetoothSerial::class
    )

    val protocols = mapOf(
        "4ByteProtocol" to My4ByteProtocol::class
    )

    fun instantiateDriver(driverName: String, protocolName: String, parameters: Any?): Backend {

        val protocolType = protocols[protocolName]
        if (protocolType is Protocol) {
            val obj = protocolType.objectInstance!!.getInstance()
        }
        TODO("see below")
        // lookup protocol class
        // instantiate protocol object
        // lookup driver class
        // instantiate driver with given protocol object and if present paramaters
        // return driver object
    }

}

package ch.semi.ledc

import ch.semi.ledc.controllers.Backend
import ch.semi.ledc.controllers.BluetoothSerial
import ch.semi.ledc.protocols.My4ByteProtocol
import ch.semi.ledc.protocols.Protocol
import ch.semi.ledc.utils.callIgnoreNull
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor


class Factory {
    companion object {

        val drivers = mapOf<String, KClass<out Backend>>(
            "RN42-CDB6" to BluetoothSerial::class
        )

        val protocols = mapOf<String, KClass<out Protocol>>(
            "4ByteProtocol" to My4ByteProtocol::class
        )

    }

    fun instantiateDriver(driverName: String, protocolName: String, parameters: Any?): Backend? =
        protocols[protocolName]?.createInstance()?.let { proto ->
            val driverConstructor = drivers[driverName]?.primaryConstructor
            driverConstructor?.callIgnoreNull(proto, parameters)
        }


}

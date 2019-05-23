package ch.semi.ledc.backend

interface Backend {

    fun connect()

    fun disconnect()

    fun transfer()

}
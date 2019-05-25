package ch.semi.ledc.controllers

import ch.semi.ledc.protocols.Protocol

abstract class Backend (val procotol: Protocol){

    abstract fun connect()

    abstract fun disconnect()

    abstract fun transfer()

}
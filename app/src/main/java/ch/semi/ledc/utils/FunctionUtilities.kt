package ch.semi.ledc.utils

import kotlin.reflect.KFunction


fun <T, A, N> KFunction<T>.callIgnoreNull(arg: A, nullable: N?) =
    if(nullable != null) {
        call(arg, nullable)
    } else {
        call(arg)
    }

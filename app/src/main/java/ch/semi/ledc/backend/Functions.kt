package ch.semi.ledc.backend

enum class Functions(val index: Byte) {

    ON_OFF(0),
    SHUTTER(2),
    RAINBOW(3);

    companion object {
        private val map = Functions.values().associateBy(Functions::index)
        fun fromIndex(type: Byte) = map[type]

    }

    public fun getBytePattern(): Byte {
        return (0b00000001 shl this.index.toInt()).toByte()
    }
}
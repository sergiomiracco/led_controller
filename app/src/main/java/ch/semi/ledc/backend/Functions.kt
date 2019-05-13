package ch.semi.ledc.backend

enum class Functions(val index: Byte) {

    OFF(0),
    ON(1),
    SHUTTER(2),
    RAINBOW(3);

    public fun getBytePattern(): Byte {
        return (0b00000001 shl this.index.toInt()).toByte()
    }
}
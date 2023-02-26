package com.arjental.taimukka.other.utils.percentage

fun Long.divideToPercent(divideTo: Long): Float {
    if (divideTo == 0L) return 0f
    return this / divideTo.toFloat()
}

fun Int.divideToPercent(divideTo: Int): Float {
    if (divideTo == 0) return 0f
    return this / divideTo.toFloat()
}
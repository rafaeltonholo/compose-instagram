package dev.tonholo.composeinstagram.extension

import java.text.NumberFormat
import java.util.TreeMap

fun Int.toInternetString(): String {
    if (this < 10_000) {
        return NumberFormat.getInstance().format(this)
    }

    val suffixes = TreeMap(
        mapOf(
            1_000 to "K",
            1_000_000 to "M",
            1_000_000_000 to "B",
        )
    )
    val (divisor, suffix) = suffixes.floorEntry(this) ?: throw Exception("can't parse $this to internet string")
    val integer = this / (divisor / 100)
    val hasDecimal = integer < 100000 && (integer / 100.0).compareTo(integer / 100) != 0

    return if (hasDecimal) {
        "${integer / 100.0}$suffix"
    } else {
        "${integer / 100}$suffix"
    }
}

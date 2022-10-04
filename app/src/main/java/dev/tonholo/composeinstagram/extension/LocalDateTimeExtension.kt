package dev.tonholo.composeinstagram.extension

import java.time.LocalDateTime
import java.time.ZoneId

private const val ONE_MINUTE = 60L
private const val ONE_HOUR = ONE_MINUTE * 60
private const val ONE_DAY = ONE_HOUR * 24
private const val ONE_WEEK = ONE_DAY * 7
private const val ONE_YEAR = ONE_DAY * 365

fun LocalDateTime?.toTimestamp(): String {
    return this?.let { date ->
        val zone = ZoneId.systemDefault()
        val timeDelta = LocalDateTime.now().atZone(zone).toEpochSecond() - date.atZone(zone).toEpochSecond()
        return when {
            timeDelta > ONE_YEAR -> "${timeDelta / ONE_YEAR} years ago"
            timeDelta >= ONE_WEEK -> "${timeDelta / ONE_WEEK} weeks ago"
            timeDelta > ONE_DAY -> "${timeDelta / ONE_DAY} days ago"
            timeDelta > ONE_HOUR -> "${timeDelta / ONE_HOUR} hours ago"
            timeDelta > ONE_MINUTE -> "${timeDelta / ONE_MINUTE} minutes ago"
            else -> "now"
        }
    } ?: ""
}

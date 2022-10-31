package dev.tonholo.composeinstagram.extension

import java.time.LocalDateTime
import java.time.ZoneId

private const val ONE_MINUTE = 60L
private const val ONE_HOUR = ONE_MINUTE * 60
private const val ONE_DAY = ONE_HOUR * 24
private const val ONE_WEEK = ONE_DAY * 7
private const val ONE_YEAR = ONE_DAY * 365

fun LocalDateTime?.toTimestamp(
    yearsLabel: String = "years ago",
    weeksLabel: String = "weeks ago",
    daysLabel: String = "days ago",
    hoursLabel: String = "hours ago",
    minutesLabel: String = "minutes ago",
    nowLabel: String = "now",
): String {
    return this?.let { date ->
        val zone = ZoneId.systemDefault()
        val timeDelta = LocalDateTime.now().atZone(zone).toEpochSecond() - date.atZone(zone).toEpochSecond()
        return when {
            timeDelta > ONE_YEAR -> "${timeDelta / ONE_YEAR} $yearsLabel"
            timeDelta >= ONE_WEEK -> "${timeDelta / ONE_WEEK} $weeksLabel"
            timeDelta > ONE_DAY -> "${timeDelta / ONE_DAY} $daysLabel"
            timeDelta > ONE_HOUR -> "${timeDelta / ONE_HOUR} $hoursLabel"
            timeDelta > ONE_MINUTE -> "${timeDelta / ONE_MINUTE} $minutesLabel"
            else -> nowLabel
        }
    } ?: ""
}

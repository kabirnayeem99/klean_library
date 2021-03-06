package io.github.kabirnayeem99.klean.ktx


import java.text.SimpleDateFormat
import java.util.*

fun Date.withTime(hour: Int, minute: Int, second: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this

    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.SECOND, second)
    calendar.set(Calendar.MILLISECOND, 0)

    return calendar.time
}

fun Date.startOfDay(): Date {
    return this.withTime(hour = 0, minute = 0, second = 0)
}


fun Date.stringWithFormat(format: String, locale: Locale = Locale.getDefault()): String {
    val dateFormat = SimpleDateFormat(format, locale)
    return dateFormat.format(this)
}

fun Date.toUtcFormat(format: String, locale: Locale = Locale.getDefault()): String {
    val dateFormat = SimpleDateFormat(format, locale)
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return dateFormat.format(this)
}

val Date.iso8601: String
    get() {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        format.timeZone = TimeZone.getTimeZone("UTC")
        return format.format(this)
    }

fun Date.roundToMinute() {
    minutes = if (seconds < 30) minutes else (minutes + 1) % 60
    seconds = 0
}

fun Date.roundToFiveMinutes() {
    minutes = if (minutes % 5 < 3) minutes - minutes % 5 else minutes + 5 - minutes % 5
    seconds = 0
}

fun Date.roundToTenMinutes() {
    minutes = if (minutes % 10 < 6) minutes - minutes % 10 else minutes + 10 - minutes % 10
    seconds = 0
}

fun Date.roundToFifteenMinutes() {
    minutes = if (minutes % 15 < 8) minutes - minutes % 15 else minutes + 15 - minutes % 15
    seconds = 0
}

fun Date.roundToHalfHour() {
    minutes = if (minutes % 30 < 15) minutes - minutes % 30 else minutes + 30 - minutes % 30
    seconds = 0
}

fun Date.roundToHour() {
    hours = if (minutes < 30) hours else hours + 1
    minutes = 0
    seconds = 0
}

var Date.calendar: Calendar
    get() = Calendar.getInstance().apply { time = this@calendar }
    set(value) {
        time = value.timeInMillis
    }

val Date.isInFuture get() = this > Date()

val Date.isInPast get() = this < Date()

val Date.isToday: Boolean
    get() {
        val calToday = Calendar.getInstance()
        return calToday.get(Calendar.ERA) == calendar.get(Calendar.ERA) &&
                calToday.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                calToday.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)
    }

val Date.isTomorrow: Boolean
    get() {
        val tomorrow = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }
        return tomorrow.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                tomorrow.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)
    }

fun Date.millisecondsSince(date: Date) = (time - date.time)
fun Date.secondsSince(date: Date): Double = millisecondsSince(date) / 1000.0
fun Date.minutesSince(date: Date): Double = secondsSince(date) / 60
fun Date.hoursSince(date: Date): Double = minutesSince(date) / 60
fun Date.daysSince(date: Date): Double = hoursSince(date) / 24
fun Date.weeksSince(date: Date): Double = daysSince(date) / 7
fun Date.monthsSince(date: Date): Double = weeksSince(date) / 4
fun Date.yearsSince(date: Date): Double = monthsSince(date) / 12

fun Date.plus(calendarField: Int, amount: Int, modify: Boolean = false): Date {
    // This can't be replaced with the calendar field
    val cal = calendar.apply { add(calendarField, amount) }
    if (modify) {
        calendar = cal
    }
    return cal.time
}

fun Date.minus(calendarField: Int, amount: Int, modify: Boolean = false) =
    plus(calendarField, -amount, modify)
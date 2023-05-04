package hu.bme.aut.bethere.utils

import android.annotation.SuppressLint
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern

private const val EMAIL_PATTERN =
    "^[_A-Za-z\\d-+]+(\\.[_A-Za-z\\d-]+)*@[A-Za-z\\d-]+(\\.[A-Za-z\\d]+)*(\\.[A-Za-z]{2,})$"
private const val PASS_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$"

fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && Pattern.compile(EMAIL_PATTERN).matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.isNotBlank() &&
            Pattern.compile(PASS_PATTERN).matcher(this).matches()
}

fun String.passwordMatches(repeated: String): Boolean {
    return this == repeated
}

fun String.removeEmptyLines(): String {
    var tmp = this
    while (tmp[0].isWhitespace()) {
        tmp = tmp.drop(1)
    }
    while (tmp[tmp.length - 1].isWhitespace()) {
        tmp = tmp.dropLast(1)
    }
    return tmp
}

@SuppressLint("SimpleDateFormat")
fun Timestamp.toSimpleString(): String {
    val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm")
    val milliseconds = this.seconds * 1000 + this.nanoseconds / 1000000
    val date = Date(milliseconds)
    return sdf.format(date).toString()
}

fun Timestamp.toLocalDate(): LocalDate {
    val date = this.toSimpleString()
    return LocalDate.of(
        date.substring(0, 4).toInt(),
        date.substring(5, 7).toInt(),
        date.substring(8, 10).toInt()
    )
}

fun Timestamp.toLocalTime(): LocalTime {
    val date = this.toSimpleString()
    return LocalTime.of(date.substring(11, 13).toInt(), date.substring(14, 16).toInt())
}

fun toTimestamp(date: LocalDate, time: LocalTime): Timestamp {
    var dateString = "${date.year}."
    dateString += if (date.monthValue < 10) "0${date.monthValue}." else "${date.monthValue}."
    dateString += if (date.dayOfMonth < 10) "0${date.dayOfMonth}" else "${date.dayOfMonth}"

    var timeString = ""
    timeString += if (time.hour < 10) "0${time.hour}:" else "${time.hour}:"
    timeString += if (time.minute < 10) "0${time.minute}" else "${time.minute}"

    val d = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy.MM.dd"))
    val t = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"))
    val dTime = d.atStartOfDay(ZoneId.systemDefault()).toInstant()

    return Timestamp(dTime.epochSecond + t.toSecondOfDay(), 0)
}
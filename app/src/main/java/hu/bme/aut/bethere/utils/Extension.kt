package hu.bme.aut.bethere.utils

import android.annotation.SuppressLint
import android.util.Patterns
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern

private const val MIN_PASS_LENGTH = 8
private const val PASS_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$"

fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.isNotBlank() &&
            this.length >= MIN_PASS_LENGTH &&
            Pattern.compile(PASS_PATTERN).matcher(this).matches()
}

fun String.passwordMatches(repeated: String): Boolean {
    return this == repeated
}

@SuppressLint("SimpleDateFormat")
fun Timestamp.toSimpleString(): String {
    val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm")
    val milliseconds = this.seconds * 1000 + this.nanoseconds / 1000000
    val date = Date(milliseconds)
    return sdf.format(date).toString()
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
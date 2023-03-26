package hu.bme.aut.bethere.utils

import android.annotation.SuppressLint
import android.util.Patterns
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

private const val MIN_PASS_LENGTH = 8
private const val PASS_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"

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
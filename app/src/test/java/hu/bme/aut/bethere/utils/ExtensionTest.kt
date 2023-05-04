package hu.bme.aut.bethere.utils

import com.google.common.truth.Truth.assertThat
import com.google.firebase.Timestamp
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class ExtensionTest {

    @Test
    fun isValidEmail_ReturnsTrue() {
        assertThat("test@test.com".isValidEmail()).isTrue()
    }

    @Test
    fun isValidEmail_ReturnsFalse() {
        assertThat("@test.com".isValidEmail()).isFalse()
    }

    @Test
    fun isValidPassword_ReturnsTrue() {
        assertThat("123456Aa".isValidPassword()).isTrue()
    }

    @Test
    fun isValidPassword_ReturnsFalse() {
        assertThat("12345678".isValidPassword()).isFalse()
    }

    @Test
    fun passwordMatches_ReturnsTrue() {
        assertThat("1234".passwordMatches("1234")).isTrue()
    }

    @Test
    fun passwordMatches_ReturnsFalse() {
        assertThat("1234".passwordMatches("12345")).isFalse()
    }

    @Test
    fun toSimpleString_ReturnsTrue() {
        assertThat(Timestamp(1681661940, 0).toSimpleString()).isEqualTo("2023.04.16 18:19")
    }

    @Test
    fun toSimpleString_ReturnsFalse() {
        assertThat(Timestamp(1681661940, 0).toSimpleString()).isNotEqualTo("2023.04.16 18:20")
    }

    @Test
    fun toTimestamp_ReturnsTrue() {
        assertThat(toTimestamp(LocalDate.of(2023, 4, 16), LocalTime.of(18, 19)))
            .isEqualTo(Timestamp(1681661940, 0))
    }

    @Test
    fun removeEmptyLines_ReturnsExpected() {
        assertThat("\n  A \na \n ".removeEmptyLines()).isEqualTo("A \na")
    }

    @Test
    fun toLocalDate_ReturnsExpected() {
        assertThat(Timestamp(1681661940, 0).toLocalDate()).isEqualTo(LocalDate.of(2023, 4, 16))
    }

    @Test
    fun toLocalTime_ReturnsExpected() {
        assertThat(Timestamp(1681661940, 0).toLocalTime()).isEqualTo(LocalTime.of(18, 19))
    }
}
package hu.bme.aut.bethere.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Event(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val date: Timestamp = Timestamp.now(),
    val location: String = "",
    val users: MutableList<String> = mutableListOf(),
    val messages: MutableList<Message> = mutableListOf()
) : Parcelable {
    fun doesMatchSearchQuery(query: String) = name.contains(query, ignoreCase = true)
}

package hu.bme.aut.bethere.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Event(
    val id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var date: Timestamp = Timestamp.now(),
    var location: String = "",
    var users: MutableList<String> = mutableListOf(),
    val messages: MutableList<Message> = mutableListOf()
) : Parcelable {
    fun doesMatchSearchQuery(query: String) = name.contains(query, ignoreCase = true)
}

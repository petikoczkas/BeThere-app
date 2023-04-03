package hu.bme.aut.bethere.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String = "",
    var name: String = "",
    var photo: String = "",
    val friends: MutableList<String> = mutableListOf(),
    val events: MutableList<String> = mutableListOf()
) : Parcelable {
    fun doesMatchSearchQuery(query: String) = name.contains(query, ignoreCase = true)
}



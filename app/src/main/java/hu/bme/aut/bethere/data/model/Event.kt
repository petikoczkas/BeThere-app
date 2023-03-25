package hu.bme.aut.bethere.data.model

import java.util.*

data class Event(
    val id: String = "",
    val name: String = "",
    val date: Date = Date(),
    val location: String = "",
    val users: List<String> = mutableListOf(),
    val messages: List<Message> = mutableListOf()
) {
    fun doesMatchSearchQuery(query: String) = name.contains(query, ignoreCase = true)
}

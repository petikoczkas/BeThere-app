package hu.bme.aut.bethere.data.model

import java.util.*

data class Event(
    val id: String,
    val name: String,
    val date: Date,
    val location: String,
    val users: List<User>,
    val messages: List<Message>
)

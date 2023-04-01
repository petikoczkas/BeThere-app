package hu.bme.aut.bethere.ui.screen.event

import com.google.firebase.Timestamp

sealed class EventDetailsUiState {

    data class EventDetailsLoaded(
        val eventId: String,
        val eventName: String,
        val eventDate: Timestamp,
        val eventLocation: String,
    ) : EventDetailsUiState()

    object EventDetailsInit : EventDetailsUiState()

    object EventDetailsSaved : EventDetailsUiState()
}
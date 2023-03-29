package hu.bme.aut.bethere.ui.screen.event

import com.google.firebase.Timestamp

sealed class EventDetailsUiState {

    data class EventDetailsLoaded(
        val eventName: String,
        val eventDate: Timestamp,
        val eventLocation: String,
    ) : EventDetailsUiState()

    object EventDetails : EventDetailsUiState()

    object EventDetailsSaved : EventDetailsUiState()
}
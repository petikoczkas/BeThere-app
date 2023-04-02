package hu.bme.aut.bethere.ui.screen.event

import com.google.firebase.Timestamp
import hu.bme.aut.bethere.data.model.Event

sealed class EventDetailsUiState {

    data class EventDetailsLoaded(
        val name: String = "",
        val date: Timestamp = Timestamp.now(),
        val location: String = "",
        val event: Event
    ) : EventDetailsUiState()

    object EventDetailsInit : EventDetailsUiState()

    object EventDetailsSaved : EventDetailsUiState()
}
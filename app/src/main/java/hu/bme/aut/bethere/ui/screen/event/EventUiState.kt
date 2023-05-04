package hu.bme.aut.bethere.ui.screen.event

sealed class EventUiState {

    object EventInit : EventUiState()

    object EventLoaded : EventUiState()
}

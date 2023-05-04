package hu.bme.aut.bethere.ui.screen.home

sealed class HomeUiState {

    object HomeInit : HomeUiState()

    object HomeLoaded : HomeUiState()
}
package hu.bme.aut.bethere.ui.screen.search

sealed class SearchScreenUiState {
    object SearchScreenInit: SearchScreenUiState()
    object SearchScreenAddFriends: SearchScreenUiState()
    object SearchScreenAddMembers: SearchScreenUiState()
}
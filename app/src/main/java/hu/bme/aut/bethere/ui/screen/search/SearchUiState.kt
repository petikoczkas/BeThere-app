package hu.bme.aut.bethere.ui.screen.search

sealed class SearchUiState {
    object SearchInit : SearchUiState()
    object SearchAddFriends : SearchUiState()
    object SearchAddMembers : SearchUiState()
}
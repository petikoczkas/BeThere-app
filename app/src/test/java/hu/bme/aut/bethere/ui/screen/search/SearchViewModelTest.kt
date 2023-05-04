package hu.bme.aut.bethere.ui.screen.search

import com.google.common.truth.Truth.assertThat
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.BeTherePresenter
import hu.bme.aut.bethere.utils.Constants
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.Before

import org.junit.Test

class SearchViewModelTest {

    private lateinit var searchViewModel: SearchViewModel
    private val beTherePresenter: BeTherePresenter = mockk()

    @Before
    fun setUp() {
        clearAllMocks()
        searchViewModel = SearchViewModel(beTherePresenter)
    }

    @Test
    fun getAllUser_ReturnsEmptyList() {
        assertThat(searchViewModel.allUser.value).isNull()
    }

    @Test
    fun getCurrentUser_ReturnsEmptyList() {
        assertThat(searchViewModel.currentUser.name).isEmpty()
    }

    @Test
    fun setCurrentUser_ReturnsExpected() {
        searchViewModel.currentUser.name = "test"
        assertThat(searchViewModel.currentUser.name).isEqualTo("test")
    }

    @Test
    fun getFriends_ReturnsEmptyList() {
        assertThat(searchViewModel.friends).isEmpty()
    }

    @Test
    fun getOthers_ReturnsEmptyList() {
        assertThat(searchViewModel.others).isEmpty()
    }

    @Test
    fun getSearchText_ReturnsEmpty() {
        assertThat(searchViewModel.searchText.value).isEmpty()
    }

    @Test
    fun getSearchedUsers_ReturnsEmptyList() {
        assertThat(searchViewModel.searchedUsers.value).isEmpty()
    }

    @Test
    fun onSearchTextChange_ReturnsExpected() {
        searchViewModel.onSearchTextChange("test")
        assertThat(searchViewModel.searchText.value).isEqualTo("test")
    }

    @Test
    fun setSearchedUsers_ReturnsExpected() {
        val user = User(id = "test")
        searchViewModel.setSearchedUsers(listOf(user))
        assertThat(searchViewModel.searchedUsers.value).isEqualTo(mutableListOf(user))
    }

    @Test
    fun separateUsers_ReturnsExpected() {
        val user = User(id = "test")
        searchViewModel.separateUsers(listOf(user))
        assertThat(searchViewModel.others).isEqualTo(mutableListOf(user))
    }

    @Test
    fun removeUser_ReturnsEmpty() {
        val user = User(id = "test")
        searchViewModel.separateUsers(listOf(user))
        searchViewModel.removeUser(user, false)
        assertThat(searchViewModel.others).isEmpty()
    }

    @Test
    fun addUserToSelectedUsers_ReturnsExpected() {
        val user = User(id = "test")
        searchViewModel.addUserToSelectedUsers(user)
        assertThat(Constants.eventMembers).isEqualTo(mutableListOf(user.id))
    }

    @Test
    fun handledAddFriendFailedEvent_ReturnsEmpty() {
        searchViewModel.handledAddFriendFailedEvent()
        assertThat(searchViewModel.addFriendFailedEvent.value.isAddFriendFailed).isFalse()
    }

    @Test
    fun setUiState_ReturnsExpected() {
        searchViewModel.setUiState(false)
        assertThat(searchViewModel.uiState.value).isEqualTo(SearchUiState.SearchAddMembers)
    }
}
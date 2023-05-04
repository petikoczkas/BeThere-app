package hu.bme.aut.bethere.ui.screen.home


import com.google.common.truth.Truth.assertThat
import hu.bme.aut.bethere.data.model.Event
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.BeTherePresenter
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private val beTherePresenter: BeTherePresenter = mockk()

    @Before
    fun setUp() {
        clearAllMocks()
        homeViewModel = HomeViewModel(beTherePresenter)
        homeViewModel.currentUser = User(name = "Test User")
    }

    @Test
    fun getCurrentUser_ReturnsExpected() {
        assertThat(homeViewModel.currentUser.name).isEqualTo("Test User")
    }

    @Test
    fun setCurrentUser_IsEqualToExpected() {
        homeViewModel.currentUser = User(name = "Test User2")
        assertThat(homeViewModel.currentUser.name).isEqualTo("Test User2")
    }

    @Test
    fun setSearchedEvents_IsEqualToExpected() {
        val events = listOf(Event(name = "1"), Event(name = "2"), Event(name = "3"))
        homeViewModel.setSearchedEvents(events)
        assertThat(homeViewModel.searchedEvents.value).isEqualTo(events)
    }

    @Test
    fun onSearchTextChange_IsEqualToExpected() {
        homeViewModel.onSearchTextChange("event")
        assertThat(homeViewModel.searchText.value).isEqualTo("event")
    }
}

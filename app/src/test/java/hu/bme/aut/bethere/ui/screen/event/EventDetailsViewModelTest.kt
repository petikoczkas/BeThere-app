package hu.bme.aut.bethere.ui.screen.event


import com.google.common.truth.Truth.assertThat
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.BeTherePresenter
import hu.bme.aut.bethere.utils.Constants
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class EventDetailsViewModelTest {

    private lateinit var eventDetailsViewModel: EventDetailsViewModel
    private val beTherePresenter: BeTherePresenter = mockk()

    @Before
    fun setUp() {
        clearAllMocks()
        eventDetailsViewModel = EventDetailsViewModel(beTherePresenter)
        Constants.eventMembers.clear()
    }

    @Test
    fun getUiState() {
        assertThat(eventDetailsViewModel.uiState.value).isEqualTo(EventDetailsUiState.EventDetailsInit)
    }

    @Test
    fun getUsers() {
        assertThat(eventDetailsViewModel.users.value).isNull()
    }

    @Test
    fun getMembers() {
        assertThat(eventDetailsViewModel.members).isEqualTo(emptyList<User>())
    }

    @Test
    fun removeButtonOnClick() {
        val user = User(id = "test")
        Constants.eventMembers.add(user.id)
        eventDetailsViewModel.removeButtonOnClick(user)
        assertThat(Constants.eventMembers).isEqualTo(emptyList<String>())
    }

    @Test
    fun getEventMembers() {
        val user = User(id = "test")
        val currentUser = User(id = "test2")
        Constants.eventMembers.add(user.id)
        eventDetailsViewModel.getEventMembers(listOf(user), currentUser)
        assertThat(eventDetailsViewModel.members.size).isEqualTo(2)
    }

    @Test
    fun handledSaveEventFailedEvent() {
        eventDetailsViewModel.handledSaveEventFailedEvent()
        assertThat(eventDetailsViewModel.saveEventFailedEvent.value.isSaveEventFailed).isFalse()
    }
}
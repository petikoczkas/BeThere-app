package hu.bme.aut.bethere.ui.screen.event

import com.google.common.truth.Truth.assertThat
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.BeTherePresenter
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.Before

import org.junit.Test

class EventViewModelTest {

    private lateinit var eventViewModel: EventViewModel
    private val beTherePresenter: BeTherePresenter = mockk()

    @Before
    fun setUp() {
        clearAllMocks()
        eventViewModel = EventViewModel(beTherePresenter)
    }

    @Test
    fun getUiState_ReturnsExpected() {
        assertThat(eventViewModel.uiState.value).isEqualTo(EventUiState.EventInit)
    }

    @Test
    fun getUsers_ReturnsNull() {
        assertThat(eventViewModel.users.value).isNull()
    }

    @Test
    fun getEvent_ReturnsEmpty() {
        assertThat(eventViewModel.event.value).isNull()
    }

    @Test
    fun getMessageText_ReturnsEmpty() {
        assertThat(eventViewModel.messageText.value).isEmpty()
    }

    @Test
    fun findUserNameById_ReturnsExpected() {
        val user = User(id = "test", name = "test")
        val user2 = User(id = "test2", name = "test2")
        assertThat(
            eventViewModel.findUserNameById(
                "test",
                listOf(user, user2)
            )
        ).isEqualTo(user.name)
    }

    @Test
    fun findUserProfileById_ReturnsExpected() {
        val user = User(id = "test", photo = "test")
        val user2 = User(id = "test2", photo = "test2")
        assertThat(
            eventViewModel.findUserProfileById(
                "test",
                listOf(user, user2)
            )
        ).isEqualTo(user.photo)
    }

    @Test
    fun onMessageTextChange_ReturnsExpected() {
        eventViewModel.onMessageTextChange("test")
        assertThat(eventViewModel.messageText.value).isEqualTo("test")
    }

    @Test
    fun handledSendMessageFailedEvent_ReturnsFalse() {
        eventViewModel.handledSendMessageFailedEvent()
        assertThat(eventViewModel.sendMessageFailedEvent.value.isSendMessageFailed).isFalse()
    }
}
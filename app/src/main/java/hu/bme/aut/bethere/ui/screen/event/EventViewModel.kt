package hu.bme.aut.bethere.ui.screen.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.bethere.data.model.Event
import hu.bme.aut.bethere.data.model.Message
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.BeTherePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val beTherePresenter: BeTherePresenter
) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    private val _messageText = MutableStateFlow("")
    val messageText = _messageText.asStateFlow()

    private val _sendMessageFailedEvent =
        MutableStateFlow(SendMessageFailure(isSendMessageFailed = false, exception = null))
    val sendMessageFailedEvent = _sendMessageFailedEvent.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                beTherePresenter.getUsers().collect {
                    _users.postValue(it)
                }
            }
        }
    }

    fun getEvent(eventId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                beTherePresenter.getCurrentEvent(eventId = eventId).collect {
                    _event.postValue(it)
                }
            }
        }
    }

    fun findUserNameById(id: String, users: List<User>?): String {
        users?.let {
            for (u in it) {
                if (u.id == id) return u.name
            }
        }
        return ""
    }

    fun onMessageTextChange(text: String) {
        _messageText.value = text
    }

    fun sendMessage(
        currentUser: User,
        event: Event,
        text: String
    ) {
        if (text.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    event.messages.add(
                        Message(
                            id = UUID.randomUUID().toString(),
                            sentBy = currentUser.id,
                            text = text
                        )
                    )
                    beTherePresenter.updateEvent(event = event)
                    onMessageTextChange("")
                } catch (e: Exception) {
                    _sendMessageFailedEvent.value =
                        SendMessageFailure(isSendMessageFailed = true, exception = e)
                }
            }
        }
    }

    fun handledSendMessageFailedEvent() {
        _sendMessageFailedEvent.update { _sendMessageFailedEvent.value.copy(isSendMessageFailed = false) }
    }

    data class SendMessageFailure(
        val isSendMessageFailed: Boolean,
        val exception: Exception?
    )
}
package hu.bme.aut.bethere.ui.screen.event

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.bethere.data.model.Event
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.BeTherePresenter
import hu.bme.aut.bethere.ui.screen.event.EventDetailsUiState.*
import hu.bme.aut.bethere.utils.Constants
import hu.bme.aut.bethere.utils.toTimestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val beTherePresenter: BeTherePresenter
) : ViewModel() {

    private val _uiState = MutableStateFlow<EventDetailsUiState>(EventDetailsInit)
    val uiState: StateFlow<EventDetailsUiState> = _uiState.asStateFlow()

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _members = mutableStateListOf<User>()
    val members: List<User> = _members

    private val removedUsers = mutableListOf<User>()

    private val _saveEventFailedEvent =
        MutableStateFlow(SaveEventFailure(isSaveEventFailed = false, exception = null))
    val saveEventFailedEvent = _saveEventFailedEvent.asStateFlow()

    fun getUsers() {
        clearEventMembers()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                beTherePresenter.getUsers().collect {
                    _users.postValue(it)
                }
            }
        }
    }

    fun setEvent(eventId: String, currentUser: User) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (eventId != "new") {
                    beTherePresenter.getCurrentEvent(eventId = eventId).collect {
                        _uiState.value = EventDetailsLoaded(
                            event = it,
                            name = it.name,
                            date = it.date,
                            location = it.location
                        )
                        setEventMembers(members = it.users)
                    }
                } else {
                    _uiState.value = EventDetailsLoaded(
                        event = Event()
                    )
                    setEventMembers(members = listOf(currentUser.id))
                }
            }
        }
    }

    private fun setEventMembers(members: List<String>) {
        for (m in members) Constants.eventMembers.add(m)
    }

    fun saveButtonOnClick() {
        viewModelScope.launch {
            try {
                val event = (_uiState.value as EventDetailsLoaded).event
                event.name = (_uiState.value as EventDetailsLoaded).name
                event.date = (_uiState.value as EventDetailsLoaded).date
                event.location = (_uiState.value as EventDetailsLoaded).location
                event.users = Constants.eventMembers
                beTherePresenter.updateEvent(event = event)
                for (m in _members) {
                    if (!m.events.contains(event.id)) {
                        m.events.add(event.id)
                        beTherePresenter.updateUser(m)
                    }
                }
                for (removed in removedUsers) {
                    if (!_members.contains(removed)) {
                        removed.events.remove(event.id)
                        beTherePresenter.updateUser(removed)
                    }
                }
                clearEventMembers()
                _uiState.value = EventDetailsSaved
            } catch (e: Exception) {
                _saveEventFailedEvent.value =
                    SaveEventFailure(isSaveEventFailed = true, exception = e)
            }
        }
    }

    fun removeButtonOnClick(user: User) {
        Constants.eventMembers.remove(user.id)
        _members.remove(user)
        removedUsers.add(user)
    }

    private fun clearEventMembers() {
        Constants.eventMembers.clear()
    }

    fun getEventMembers(users: List<User>?, currentUser: User) {
        _members.clear()
        _members.add(currentUser)
        val eventMembers = Constants.eventMembers
        users?.let {
            for (m in eventMembers) {
                for (u in users) {
                    if (u.id == m) {
                        _members.add(u)
                        break
                    }
                }
            }
        }
    }

    fun onNameChange(name: String) {
        _uiState.update { (_uiState.value as EventDetailsLoaded).copy(name = name) }
    }

    fun onDateChange(date: LocalDate, time: LocalTime) {
        _uiState.update {
            (_uiState.value as EventDetailsLoaded).copy(
                date = toTimestamp(
                    date,
                    time
                )
            )
        }
    }

    fun onLocationChange(location: String) {
        _uiState.update { (_uiState.value as EventDetailsLoaded).copy(location = location) }
    }

    fun handledSaveEventFailedEvent() {
        _saveEventFailedEvent.update { _saveEventFailedEvent.value.copy(isSaveEventFailed = false) }
    }

    data class SaveEventFailure(
        val isSaveEventFailed: Boolean,
        val exception: Exception?
    )
}
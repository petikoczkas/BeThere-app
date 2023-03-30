package hu.bme.aut.bethere.ui.screen.event

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.BeTherePresenter
import hu.bme.aut.bethere.ui.screen.event.EventDetailsUiState.EventDetails
import hu.bme.aut.bethere.ui.screen.event.EventDetailsUiState.EventDetailsLoaded
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

    private val _uiState = MutableStateFlow<EventDetailsUiState>(EventDetails)
    val uiState: StateFlow<EventDetailsUiState> = _uiState.asStateFlow()

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                beTherePresenter.getUsers().collect {
                    _users.postValue(it)
                }
            }
        }
    }

    fun setEvent(eventId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (eventId != "new") {
                    beTherePresenter.getCurrentEvent(eventId = eventId).collect {
                        _uiState.value = EventDetailsLoaded(
                            eventName = it.name,
                            eventDate = it.date,
                            eventLocation = it.location
                        )
                        //Log.println(Log.INFO, "logolok", "szoszker")
                    }
                } else {
                    _uiState.value = EventDetailsLoaded(
                        eventName = "",
                        eventDate = Timestamp.now(),
                        eventLocation = ""
                    )
                    //Log.println(Log.INFO, "logolok", "haloo")
                }
            }
        }
    }


    fun buttonOnClick() {
        clearSelectedUsers()
    }

    fun clearSelectedUsers() {
        beTherePresenter.setSelectedUsersOnSearchScreen(null)
    }

    fun getEventMembers(users: List<User>?, currentUser: User, eventId: String): List<User> {
        val members = beTherePresenter.getSelectedUsersOnSearchScreen()
        if (members.isNotEmpty()) Log.println(Log.INFO, "tárolt adatok", members[0].name)
        members.add(currentUser)
        users?.let {
            for (u in users) {
                if (u.events.contains(eventId)) members.add(u)
            }
        }
        return members
    }

    fun onNameChange(name: String) {
        _uiState.update { (_uiState.value as EventDetailsLoaded).copy(eventName = name) }
    }

    fun onDateChange(date: LocalDate, time: LocalTime) {
        //Log.println(Log.INFO, "indfoosfoüa", time.hour.toString())
        _uiState.update {
            (_uiState.value as EventDetailsLoaded).copy(
                eventDate = toTimestamp(
                    date,
                    time
                )
            )
        }
    }

    fun onLocationChange(location: String) {
        _uiState.update { (_uiState.value as EventDetailsLoaded).copy(eventLocation = location) }
    }
}
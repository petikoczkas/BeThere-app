package hu.bme.aut.bethere.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.bethere.data.model.Event
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.BeTherePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val beTherePresenter: BeTherePresenter
) : ViewModel() {

    var currentUser = User()

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private var _searchedEvents = MutableStateFlow(mutableListOf<Event>())
    val searchedEvents = searchText
        .combine(_searchedEvents) { text, users ->
            if (text.length < 3) {
                users
            } else {
                users.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _searchedEvents.value
        )

    init {
        setUp()
    }

    private fun setUp() {
        viewModelScope.launch {
            delay(500)
            currentUser = beTherePresenter.getCurrentUser()
            withContext(Dispatchers.IO) {
                beTherePresenter.getCurrentUserEvents(currentUser).collect {
                    _events.postValue(it)
                }
            }
        }
    }

    fun setSearchedEvents(events: List<Event>?) {
        events?.let {
            _searchedEvents.value.clear()
            for (e in it) {
                _searchedEvents.value.add(e)
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun signOut() {
        beTherePresenter.signOut()
    }
}
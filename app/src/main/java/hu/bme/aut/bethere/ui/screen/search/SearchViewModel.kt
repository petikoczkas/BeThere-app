package hu.bme.aut.bethere.ui.screen.search

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.BeTherePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val beTherePresenter: BeTherePresenter
) : ViewModel() {

    private val _allUser = MutableLiveData<List<User>>()
    val allUser: LiveData<List<User>> = _allUser

    var currentUser = User()

    private val _friends = mutableStateListOf<User>()
    val friends: List<User> = _friends
    private val _others = mutableStateListOf<User>()
    val others: List<User> = _others


    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private var _searchedUsers = MutableStateFlow(mutableListOf<User>())
    val searchedUsers = searchText
        .combine(_searchedUsers) { text, users ->
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
            _searchedUsers.value
        )

    private val _addFriendFailedEvent =
        MutableStateFlow(AddFriendFailure(isAddFriendFailed = false, exception = null))
    val addFriendFailedEvent = _addFriendFailedEvent.asStateFlow()

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            currentUser = beTherePresenter.getCurrentUser()
            withContext(Dispatchers.IO) {
                beTherePresenter.getUsers().collect {
                    _allUser.postValue(it)
                }
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun setSearchedUsers(allUser: List<User>?) {
        allUser?.let {
            _searchedUsers.value.clear()
            for (u in it) {
                _searchedUsers.value.add(u)
            }
        }
    }

    fun separateUsers(users: List<User>) {
        _friends.clear()
        _others.clear()
        for (u in users) {
            if (currentUser.friends.contains(u.id)) _friends.add(u)
            else _others.add(u)
        }
    }

    fun addFriend(user: User) {
        viewModelScope.launch {
            try {
                currentUser.friends.add(user.id)
                beTherePresenter.updateUser(user = currentUser)
                onSearchTextChange("")
            } catch (e: Exception) {
                _addFriendFailedEvent.value =
                    AddFriendFailure(isAddFriendFailed = true, exception = e)
            }
        }
    }

    fun handledAddFriendFailedEvent() {
        _addFriendFailedEvent.update { _addFriendFailedEvent.value.copy(isAddFriendFailed = false) }
    }

    data class AddFriendFailure(
        val isAddFriendFailed: Boolean,
        val exception: Exception?
    )
}

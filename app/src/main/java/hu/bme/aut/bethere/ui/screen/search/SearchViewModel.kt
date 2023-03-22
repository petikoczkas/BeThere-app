package hu.bme.aut.bethere.ui.screen.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.bethere.data.DataOrException
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.BeTherePresenter
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val beTherePresenter: BeTherePresenter
) : ViewModel() {

    private val allUser: MutableState<DataOrException<MutableList<User>, Exception>> =
        mutableStateOf(
            DataOrException(
                mutableListOf(),
                Exception("")
            )
        )
    var currentUser = User()


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

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            allUser.value = beTherePresenter.getUsers()
            allUser.value.data?.last()?.let { currentUser = it }
            allUser.value.data?.removeLast()
            _searchedUsers.value = allUser.value.data ?: mutableListOf()
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun separateUsers(users: List<User>, isFriendList: Boolean): List<User> {
        val friends = mutableListOf<User>()
        val others = mutableListOf<User>()
        for (u in users) {
            if (currentUser.friends.contains(u.id)) friends.add(u)
            else others.add(u)
        }
        return if (isFriendList) friends.toList()
        else others.toList()
    }
}

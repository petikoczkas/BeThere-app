package hu.bme.aut.bethere.ui.screen.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.screen.search.SearchScreenUiState.SearchScreenInit
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography
import hu.bme.aut.bethere.ui.view.card.UserCard
import hu.bme.aut.bethere.ui.view.dialog.BeThereAlertDialog
import hu.bme.aut.bethere.ui.view.textfield.SearchField

@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    isAddFriendClicked: Boolean,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val addFriendFailedEvent by viewModel.addFriendFailedEvent.collectAsState()
    val allUser by viewModel.allUser.observeAsState()
    val searchedUsers by viewModel.searchedUsers.collectAsState()
    viewModel.setSearchedUsers(allUser)
    viewModel.separateUsers(searchedUsers)

    when (uiState) {
        SearchScreenInit -> {
            viewModel.setUiState(isAddFriendClicked = isAddFriendClicked)
        }
        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = MaterialTheme.beThereDimens.gapVeryLarge),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { navigator.popBackStack() },
                            modifier = Modifier
                                .padding(start = MaterialTheme.beThereDimens.gapMedium)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = null,
                                tint = MaterialTheme.colors.primary
                            )
                        }
                        Text(
                            text = stringResource(R.string.search),
                            style = MaterialTheme.beThereTypography.titleTextStyle,
                        )
                        Box(
                            modifier = Modifier
                                .size(
                                    height = MaterialTheme.beThereDimens.gapVeryTiny,
                                    width = MaterialTheme.beThereDimens.gapVeryVeryLarge
                                )
                        )
                    }
                    SearchField(
                        text = searchText,
                        onTextChange = viewModel::onSearchTextChange,
                        placeholder = stringResource(R.string.search_user_placeholder)
                    )
                    SearchList(
                        viewModel = viewModel,
                        friends = viewModel.friends,
                        others = viewModel.others,
                        isAddFriendClicked = isAddFriendClicked,
                    )
                }
            }
            if (addFriendFailedEvent.isAddFriendFailed) {
                BeThereAlertDialog(
                    title = if (isAddFriendClicked) stringResource(R.string.add_friend_failed)
                    else stringResource(R.string.add_user_to_event_failed),
                    description = addFriendFailedEvent.exception?.message.toString(),
                    onDismiss = { viewModel.handledAddFriendFailedEvent() }
                )
            }
        }
    }
}

@Composable
private fun SearchList(
    viewModel: SearchViewModel,
    friends: List<User>,
    others: List<User>,
    isAddFriendClicked: Boolean,
) {
    LazyColumn(
        modifier = Modifier
            .padding(
                start = MaterialTheme.beThereDimens.gapNormal,
                top = MaterialTheme.beThereDimens.gapMedium,
                end = MaterialTheme.beThereDimens.gapNormal,
                bottom = MaterialTheme.beThereDimens.gapNormal
            )
    ) {
        if (!isAddFriendClicked) {
            item {
                Text(
                    text = stringResource(R.string.your_friends),
                    style = MaterialTheme.beThereTypography.descriptionTextStyle,
                )
            }
            if (friends.isEmpty()) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = stringResource(R.string.no_friends))
                    }
                }
            }
            items(friends) { u ->
                UserCard(
                    text = u.name,
                    photo = u.photo,
                    modifier = Modifier
                        .padding(vertical = MaterialTheme.beThereDimens.gapSmall)
                ) {
                    IconButton(onClick = {
                        viewModel.addUserToSelectedUsers(u)
                        viewModel.removeUser(user = u, isFriend = true)
                    }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = null
                        )
                    }
                }
            }
            item {
                Divider(
                    color = MaterialTheme.colors.primary,
                    thickness = MaterialTheme.beThereDimens.dividerThickness,
                    modifier = Modifier.padding(
                        vertical = MaterialTheme.beThereDimens.gapMedium,
                    )
                )
            }
        }
        item {
            Text(
                text = stringResource(R.string.others),
                style = MaterialTheme.beThereTypography.descriptionTextStyle,
            )
        }
        items(others) { u ->
            UserCard(
                text = u.name,
                photo = u.photo,
                modifier = Modifier
                    .padding(vertical = MaterialTheme.beThereDimens.gapSmall)
            ) {
                IconButton(onClick = {
                    if (isAddFriendClicked) {
                        viewModel.addFriend(u)
                    } else {
                        viewModel.addUserToSelectedUsers(u)
                        viewModel.removeUser(user = u, isFriend = false)
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null
                    )
                }
            }
        }
    }
}
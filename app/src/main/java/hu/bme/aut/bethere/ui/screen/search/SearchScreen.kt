package hu.bme.aut.bethere.ui.screen.search

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography
import hu.bme.aut.bethere.ui.view.card.UserCard
import hu.bme.aut.bethere.ui.view.textfield.SearchField

@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    isAddFriendClicked: Boolean,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchText by viewModel.searchText.collectAsState()
    val searchedUsers by viewModel.searchedUsers.collectAsState()
    val addFriendFailedEvent by viewModel.addFriendFailedEvent.collectAsState()
    viewModel.separateUsers(searchedUsers)

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
            IconButton(
                onClick = { navigator.popBackStack() },
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = MaterialTheme.beThereDimens.gapMedium)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
            Text(
                text = stringResource(R.string.search),
                style = MaterialTheme.beThereTypography.titleTextStyle,
                modifier = Modifier.padding(
                    top = MaterialTheme.beThereDimens.gapMedium,
                    bottom = MaterialTheme.beThereDimens.gapVeryVeryLarge,
                )
            )
            SearchField(
                text = searchText,
                onTextChange = viewModel::onSearchTextChange,
                onSearchButtonClick = {}
            )
            var modifier = Modifier.fillMaxWidth()
            if (!isAddFriendClicked) {
                modifier = modifier.height(MaterialTheme.beThereDimens.userBoxHeight)
                SearchList(
                    text = stringResource(R.string.your_friends),
                    users = viewModel.friends,
                    addFriendOnClick = { },
                    modifier = modifier
                )
            }
            SearchList(
                text = stringResource(R.string.others),
                users = viewModel.others,
                addFriendOnClick = {
                    viewModel.addFriend(it)
                    viewModel.separateUsers(searchedUsers)
                },
                modifier = modifier
            )
        }
    }
    if (addFriendFailedEvent.isAddFriendFailed) {
        viewModel.handledAddFriendFailedEvent()
        Toast.makeText(
            LocalContext.current,
            "Error adding a friend",
            Toast.LENGTH_LONG
        ).show()
    }
}

@Composable
private fun SearchList(
    text: String,
    users: List<User>,
    addFriendOnClick: (u: User) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.beThereDimens.gapNormal)
    ) {
        Text(
            text = text,
            style = MaterialTheme.beThereTypography.descriptionTextStyle,
            modifier = Modifier.padding(
                start = MaterialTheme.beThereDimens.gapNormal,
            )
        )
        LazyColumn(
            modifier = Modifier
                .padding(
                    start = MaterialTheme.beThereDimens.gapNormal,
                    top = MaterialTheme.beThereDimens.gapSmall,
                    end = MaterialTheme.beThereDimens.gapNormal,
                )
        ) {
            items(users) { u ->
                UserCard(
                    text = u.name,
                    onClick = {},
                    modifier = Modifier
                        .padding(vertical = MaterialTheme.beThereDimens.gapSmall)
                ) {
                    IconButton(onClick = { addFriendOnClick(u) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}
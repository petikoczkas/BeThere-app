package hu.bme.aut.bethere.ui.screen.home

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.screen.NavGraphs
import hu.bme.aut.bethere.ui.screen.destinations.*
import hu.bme.aut.bethere.ui.screen.home.HomeUiState.HomeInit
import hu.bme.aut.bethere.ui.screen.home.HomeUiState.HomeLoaded
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.view.button.PrimaryButton
import hu.bme.aut.bethere.ui.view.card.EventCard
import hu.bme.aut.bethere.ui.view.circularprogressindicator.BeThereProgressIndicator
import hu.bme.aut.bethere.ui.view.textfield.SearchField
import hu.bme.aut.bethere.utils.ComposableLifecycle

@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val searchText by viewModel.searchText.collectAsState()
    val searchedEvents by viewModel.searchedEvents.collectAsState()
    val events by viewModel.events.observeAsState()

    viewModel.setSearchedEvents(events)

    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            viewModel.getEvents()
        }
    }
    when (uiState) {
        HomeLoaded -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Header(navigator = navigator, viewModel = viewModel)
                    SearchField(
                        text = searchText,
                        onTextChange = viewModel::onSearchTextChange,
                        placeholder = stringResource(R.string.search_event_placeholder)
                    )
                    if (events == null) {
                        BeThereProgressIndicator(
                            modifier = Modifier.weight(
                                weight = 1f,
                                fill = false
                            )
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.padding(MaterialTheme.beThereDimens.gapNormal)
                        ) {
                            if (events!!.isEmpty()) {
                                item {
                                    Text(text = stringResource(R.string.no_events))
                                }
                            } else {
                                items(searchedEvents) { e ->
                                    EventCard(
                                        text = e.name,
                                        onClick = {
                                            navigator.navigate(
                                                EventScreenDestination(
                                                    eventId = e.id,
                                                    currentUser = viewModel.currentUser
                                                )
                                            )
                                        },
                                        modifier = Modifier
                                            .padding(vertical = MaterialTheme.beThereDimens.gapSmall)
                                    )
                                }
                            }
                        }
                    }
                }
                PrimaryButton(
                    text = stringResource(R.string.new_event),
                    onClick = {
                        navigator.navigate(
                            EventDetailsScreenDestination(
                                eventId = "new",
                                currentUser = viewModel.currentUser
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = MaterialTheme.beThereDimens.gapNormal,
                            end = MaterialTheme.beThereDimens.gapNormal,
                            bottom = MaterialTheme.beThereDimens.gapNormal,
                        )
                )
            }
        }
        HomeInit -> {
            viewModel.getEvents()
        }
    }

}

@Composable
private fun Header(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.beThereDimens.gapVeryLarge + MaterialTheme.beThereDimens.gapMedium),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                viewModel.signOut()
                navigator.clearBackStack(NavGraphs.root)
                navigator.navigate(SignInScreenDestination)
            },
            modifier = Modifier.padding(start = MaterialTheme.beThereDimens.gapMedium)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
        Row {
            IconButton(
                onClick = { navigator.navigate(SearchScreenDestination(isAddFriendClicked = true)) },
                modifier = Modifier.padding(end = MaterialTheme.beThereDimens.gapMedium)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person_add),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
            IconButton(
                onClick = { navigator.navigate(SettingsScreenDestination) },
                modifier = Modifier.padding(end = MaterialTheme.beThereDimens.gapMedium)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}
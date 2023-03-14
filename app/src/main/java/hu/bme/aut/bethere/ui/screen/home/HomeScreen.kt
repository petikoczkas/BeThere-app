package hu.bme.aut.bethere.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.screen.NavGraphs
import hu.bme.aut.bethere.ui.screen.destinations.*
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.view.button.PrimaryButton
import hu.bme.aut.bethere.ui.view.card.EventCard
import hu.bme.aut.bethere.ui.view.textfield.SearchField

@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {
    var search by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.beThereDimens.gapNormal)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Header(navigator = navigator)
            SearchField(text = search, onTextChange = { search = it }, onSearchButtonClick = {})
            LazyColumn(
                modifier = Modifier.padding(MaterialTheme.beThereDimens.gapNormal)
            ) {
                items(3) {
                    EventCard(
                        text = "Event",
                        onClick = { navigator.navigate(EventScreenDestination) },
                        modifier = Modifier
                            .padding(vertical = MaterialTheme.beThereDimens.gapSmall)
                    )
                }
            }
        }
        PrimaryButton(
            text = "Add event",
            onClick = { navigator.navigate(EventDetailsScreenDestination) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.beThereDimens.gapNormal,
                    top = MaterialTheme.beThereDimens.gapNormal,
                    end = MaterialTheme.beThereDimens.gapNormal,
                    bottom = MaterialTheme.beThereDimens.gapLarge,
                )
        )
    }
}

@Composable
private fun Header(navigator: DestinationsNavigator) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                navigator.clearBackStack(NavGraphs.root)
                navigator.navigate(SignInScreenDestination)
            },
            modifier = Modifier.padding(start = MaterialTheme.beThereDimens.gapMedium)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = null
            )
        }
        Row {
            IconButton(
                onClick = { navigator.navigate(SearchScreenDestination) },
                modifier = Modifier.padding(end = MaterialTheme.beThereDimens.gapMedium)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person_add),
                    contentDescription = null
                )
            }
            IconButton(
                onClick = { navigator.navigate(SettingsScreenDestination) },
                modifier = Modifier.padding(end = MaterialTheme.beThereDimens.gapMedium)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = null
                )
            }
        }
    }
}
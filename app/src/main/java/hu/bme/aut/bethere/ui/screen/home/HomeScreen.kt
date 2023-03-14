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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.screen.NavGraphs
import hu.bme.aut.bethere.ui.screen.destinations.EventScreenDestination
import hu.bme.aut.bethere.ui.screen.destinations.SearchScreenDestination
import hu.bme.aut.bethere.ui.screen.destinations.SettingsScreenDestination
import hu.bme.aut.bethere.ui.screen.destinations.SignInScreenDestination
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.view.card.EventCard
import hu.bme.aut.bethere.ui.view.textfield.SearchField

@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {
    var search by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MaterialTheme.beThereDimens.gapNormal),
    ) {
        Header(navigator = navigator)
        SearchField(text = search, onTextChange = { search = it }, onSearchButtonClick = {})
        LazyColumn(
            modifier = Modifier.padding(MaterialTheme.beThereDimens.gapNormal)
        ) {
            items(15) {
                EventCard(
                    text = "Event",
                    onClick = { navigator.navigate(EventScreenDestination) },
                    modifier = Modifier
                        .padding(vertical = MaterialTheme.beThereDimens.gapSmall)
                )
            }
        }
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
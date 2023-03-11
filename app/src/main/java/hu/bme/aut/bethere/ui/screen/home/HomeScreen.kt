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
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.view.card.EventCard
import hu.bme.aut.bethere.ui.view.textfield.SearchField

@Composable
fun HomeScreen() {
    var search by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MaterialTheme.beThereDimens.gapNormal),
    ) {
        Header()
        SearchField(text = search, onTextChange = { search = it }, onSearchButtonClick = {})
        LazyColumn(
            modifier = Modifier.padding(MaterialTheme.beThereDimens.gapNormal)
        ) {
            items(15) {
                EventCard(
                    text = "Event",
                    onClick = {},
                    modifier = Modifier
                        .padding(vertical = MaterialTheme.beThereDimens.gapSmall)
                )
            }
        }
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(start = MaterialTheme.beThereDimens.gapMedium)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = null
            )
        }
        Row {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(end = MaterialTheme.beThereDimens.gapMedium)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person_add),
                    contentDescription = null
                )
            }
            IconButton(
                onClick = { /*TODO*/ },
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
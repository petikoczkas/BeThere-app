package hu.bme.aut.bethere.ui.screen.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.screen.destinations.EventScreenDestination
import hu.bme.aut.bethere.ui.screen.destinations.SearchScreenDestination
import hu.bme.aut.bethere.ui.theme.beThereColors
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography
import hu.bme.aut.bethere.ui.view.button.PrimaryButton
import hu.bme.aut.bethere.ui.view.card.UserCard

@Destination
@Composable
fun EventDetailsScreen(navigator: DestinationsNavigator) {
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
            Header()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.beThereDimens.gapNormal)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Members",
                        style = MaterialTheme.beThereTypography.descriptionTextStyle,
                        modifier = Modifier.padding(
                            start = MaterialTheme.beThereDimens.gapNormal,
                        )
                    )
                    IconButton(
                        onClick = { navigator.navigate(SearchScreenDestination) },
                        modifier = Modifier.padding(
                            end = MaterialTheme.beThereDimens.gapNormal
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_person_add),
                            contentDescription = null,
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .padding(
                            start = MaterialTheme.beThereDimens.gapNormal,
                            top = MaterialTheme.beThereDimens.gapSmall,
                            end = MaterialTheme.beThereDimens.gapNormal,
                        )
                ) {
                    items(15) {
                        UserCard(
                            text = "Name of client",
                            onClick = {},
                            modifier = Modifier
                                .padding(vertical = MaterialTheme.beThereDimens.gapSmall)
                        ) {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_remove),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
        PrimaryButton(
            onClick = { navigator.navigate(EventScreenDestination) },
            enabled = true,
            text = stringResource(R.string.save),
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
private fun Header() {
    var name by rememberSaveable { mutableStateOf("Event") }
    var date by rememberSaveable { mutableStateOf("2023.03.12") }
    var location by rememberSaveable { mutableStateOf("anyu") }


    val textFieldColors = TextFieldDefaults.textFieldColors(
        textColor = MaterialTheme.beThereColors.black,
        disabledTextColor = MaterialTheme.beThereColors.black,
        focusedIndicatorColor = MaterialTheme.beThereColors.black,
        unfocusedIndicatorColor = MaterialTheme.beThereColors.black,
        disabledIndicatorColor = MaterialTheme.beThereColors.transparent,
        backgroundColor = MaterialTheme.beThereColors.gray,
        cursorColor = MaterialTheme.beThereColors.black
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.beThereDimens.gapNormal)
            .clip(RoundedCornerShape(MaterialTheme.beThereDimens.textFieldCornerSize))
            .background(MaterialTheme.beThereColors.gray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.beThereDimens.gapNormal),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(modifier = Modifier.weight(1f)) {
                TextField(
                    value = "Name:",
                    onValueChange = {},
                    enabled = false,
                    textStyle = MaterialTheme.beThereTypography.descriptionTextStyle,
                    colors = textFieldColors
                )
                TextField(
                    value = "Time and date:",
                    onValueChange = {},
                    enabled = false,
                    textStyle = MaterialTheme.beThereTypography.descriptionTextStyle,
                    colors = textFieldColors
                )
                TextField(
                    value = "Location:",
                    onValueChange = {},
                    enabled = false,
                    textStyle = MaterialTheme.beThereTypography.descriptionTextStyle,
                    colors = textFieldColors
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    textStyle = MaterialTheme.beThereTypography.descriptionTextStyle,
                    colors = textFieldColors
                )
                TextField(
                    value = date,
                    onValueChange = { date = it },
                    textStyle = MaterialTheme.beThereTypography.descriptionTextStyle,
                    colors = textFieldColors
                )
                TextField(
                    value = location,
                    onValueChange = { location = it },
                    textStyle = MaterialTheme.beThereTypography.descriptionTextStyle,
                    colors = textFieldColors
                )
            }
        }
    }
}
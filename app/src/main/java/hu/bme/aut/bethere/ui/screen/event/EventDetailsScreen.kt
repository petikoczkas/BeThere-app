package hu.bme.aut.bethere.ui.screen.event

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.screen.destinations.SearchScreenDestination
import hu.bme.aut.bethere.ui.screen.event.EventDetailsUiState.*
import hu.bme.aut.bethere.ui.theme.beThereColors
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography
import hu.bme.aut.bethere.ui.view.button.PrimaryButton
import hu.bme.aut.bethere.ui.view.card.UserCard
import hu.bme.aut.bethere.ui.view.textfield.EditTextField
import hu.bme.aut.bethere.utils.toSimpleString
import java.time.LocalDate
import java.time.LocalTime

@Destination
@Composable
fun EventDetailsScreen(
    navigator: DestinationsNavigator,
    viewModel: EventDetailsViewModel = hiltViewModel(),
    eventId: String,
    currentUser: User
) {
    val users by viewModel.users.observeAsState()
    val uiState by viewModel.uiState.collectAsState()


    when (uiState) {
        is EventDetailsLoaded -> {
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
                    Header(uiState = uiState, viewModel = viewModel)
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
                                onClick = {
                                    navigator.navigate(
                                        SearchScreenDestination(
                                            isAddFriendClicked = false
                                        )
                                    )
                                },
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
                            users?.let {
                                items(
                                    viewModel.getEventMembers(
                                        users = users!!,
                                        currentUser = currentUser,
                                        eventId = eventId
                                    )
                                ) { u ->
                                    UserCard(
                                        text = u.name,
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
                }
                PrimaryButton(
                    onClick = { viewModel.buttonOnClick() },
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
        is EventDetails -> {
            viewModel.setEvent(eventId = eventId)
        }
        is EventDetailsSaved -> {
            navigator.popBackStack()
        }
    }
}

@Composable
private fun Header(
    viewModel: EventDetailsViewModel,
    uiState: EventDetailsUiState
) {
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedTime by remember {
        mutableStateOf(LocalTime.now())
    }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

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
                EditTextField(
                    text = "Name:",
                    onTextChange = {},
                    enabled = false
                )
                EditTextField(
                    text = "Time and date:",
                    onTextChange = {},
                    enabled = false,
                )
                EditTextField(
                    text = "Location:",
                    onTextChange = {},
                    enabled = false,
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                EditTextField(
                    text = (uiState as EventDetailsLoaded).eventName,
                    onTextChange = viewModel::onNameChange,
                    placeHolder = { Text(text = "Name") }
                )
                EditTextField(
                    text = uiState.eventDate.toSimpleString(),
                    enabled = false,
                    onTextChange = { },
                    placeHolder = { Text(text = "Date") },
                    modifier = Modifier.clickable { dateDialogState.show() }
                )
                EditTextField(
                    text = uiState.eventLocation,
                    onTextChange = viewModel::onLocationChange,
                    placeHolder = { Text(text = "Location") }
                )
            }
        }
        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(text = "OK") {
                    timeDialogState.show()
                }
                negativeButton(text = "CANCEL")
            }
        ) {
            datepicker(
                initialDate = pickedDate,
                title = "Pick a date",
                colors = DatePickerDefaults.colors(),
                allowedDateValidator = {
                    it >= LocalDate.now()
                }
            ) {
                pickedDate = it
            }
        }
        val timeRange =
            if (pickedDate == LocalDate.now()) LocalTime.now()..LocalTime.MAX else LocalTime.MIN..LocalTime.MAX
        MaterialDialog(
            dialogState = timeDialogState,
            buttons = {
                positiveButton(text = "OK") {
                    viewModel.onDateChange(pickedDate, pickedTime)
                }
                negativeButton(text = "CANCEL")
            }
        ) {
            timepicker(
                initialTime = pickedTime,
                title = "Pick a date",
                is24HourClock = true,
                colors = TimePickerDefaults.colors(),
                timeRange = timeRange
            ) {
                pickedTime = it
            }
        }
    }
}
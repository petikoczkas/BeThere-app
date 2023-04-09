package hu.bme.aut.bethere.ui.screen.event

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
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
import hu.bme.aut.bethere.ui.screen.destinations.HomeScreenDestination
import hu.bme.aut.bethere.ui.screen.destinations.SearchScreenDestination
import hu.bme.aut.bethere.ui.screen.event.EventDetailsUiState.*
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography
import hu.bme.aut.bethere.ui.view.button.PrimaryButton
import hu.bme.aut.bethere.ui.view.card.UserCard
import hu.bme.aut.bethere.ui.view.textfield.OutlinedEditTextField
import hu.bme.aut.bethere.utils.ComposableLifecycle
import hu.bme.aut.bethere.utils.toSimpleString
import java.time.LocalDate
import java.time.LocalTime

@Destination
@Composable
fun EventDetailsScreen(
    navigator: DestinationsNavigator,
    viewModel: EventDetailsViewModel = hiltViewModel(),
    eventId: String,
    currentUser: User,
) {
    val users by viewModel.users.observeAsState()
    val uiState by viewModel.uiState.collectAsState()
    val saveEventFailedEvent by viewModel.saveEventFailedEvent.collectAsState()

    when (uiState) {
        is EventDetailsLoaded -> {
            ComposableLifecycle { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    viewModel.getEventMembers(users = users, currentUser = currentUser)
                }
            }

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
                            text = stringResource(R.string.edit_event),
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
                                text = stringResource(R.string.members),
                                style = MaterialTheme.beThereTypography.memberTitleTextStyle,
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
                                    tint = MaterialTheme.colors.primary
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
                            items(
                                viewModel.members
                            ) { u ->
                                UserCard(
                                    text = u.name,
                                    photo = u.photo,
                                    modifier = Modifier
                                        .padding(vertical = MaterialTheme.beThereDimens.gapSmall)
                                ) {
                                    IconButton(onClick = {
                                        viewModel.removeButtonOnClick(u)
                                    }) {
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
                    onClick = { viewModel.saveButtonOnClick() },
                    text = stringResource(R.string.save),
                    enabled = (uiState as EventDetailsLoaded).name.isNotEmpty() and (uiState as EventDetailsLoaded).location.isNotEmpty(),
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
            if (saveEventFailedEvent.isSaveEventFailed) {
                viewModel.handledAddFriendFailedEvent()
                Toast.makeText(
                    LocalContext.current,
                    "Error saving the event",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        is EventDetailsInit -> {
            viewModel.setEvent(eventId = eventId, currentUser = currentUser)
        }
        is EventDetailsSaved -> {
            if (!viewModel.members.contains(currentUser)) navigator.popBackStack(
                route = HomeScreenDestination,
                inclusive = false
            )
            else navigator.popBackStack()
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

    OutlinedEditTextField(
        text = (uiState as EventDetailsLoaded).name,
        onTextChange = viewModel::onNameChange,
        placeholder = stringResource(R.string.name_of_event),
        modifier = Modifier.padding(
            start = MaterialTheme.beThereDimens.gapNormal,
            top = MaterialTheme.beThereDimens.gapNormal,
            end = MaterialTheme.beThereDimens.gapNormal,
            bottom = MaterialTheme.beThereDimens.gapMedium

        )
    )
    OutlinedEditTextField(
        text = uiState.date.toSimpleString(),
        onTextChange = {},
        placeholder = stringResource(R.string.time_and_date_placeholder),
        enabled = false,
        onClick = { dateDialogState.show() },
        modifier = Modifier
            .padding(
                horizontal = MaterialTheme.beThereDimens.gapNormal,
                vertical = MaterialTheme.beThereDimens.gapMedium
            )
    )
    OutlinedEditTextField(
        text = uiState.location,
        onTextChange = viewModel::onLocationChange,
        placeholder = stringResource(R.string.location_placeholder),
        modifier = Modifier.padding(
            horizontal = MaterialTheme.beThereDimens.gapNormal,
            vertical = MaterialTheme.beThereDimens.gapMedium
        )
    )
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = stringResource(R.string.ok)) {
                timeDialogState.show()
            }
            negativeButton(text = stringResource(R.string.cancel))
        }
    ) {
        datepicker(
            initialDate = pickedDate,
            title = stringResource(R.string.pick_date),
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
            positiveButton(text = stringResource(R.string.ok)) {
                viewModel.onDateChange(pickedDate, pickedTime)
            }
            negativeButton(text = stringResource(R.string.cancel))
        }
    ) {
        timepicker(
            initialTime = pickedTime,
            title = stringResource(R.string.pick_time),
            is24HourClock = true,
            colors = TimePickerDefaults.colors(),
            timeRange = timeRange
        ) {
            pickedTime = it
        }
    }
}
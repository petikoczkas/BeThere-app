package hu.bme.aut.bethere.ui.screen.event

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.data.model.Event
import hu.bme.aut.bethere.data.model.Message
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.screen.destinations.EventDetailsScreenDestination
import hu.bme.aut.bethere.ui.theme.beThereColors
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography
import hu.bme.aut.bethere.utils.toSimpleString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Destination
@Composable
fun EventScreen(
    navigator: DestinationsNavigator,
    viewModel: EventViewModel = hiltViewModel(),
    eventId: String,
    currentUser: User
) {
    val messageText by viewModel.messageText.collectAsState()
    viewModel.getEvent(eventId = eventId)
    val event by viewModel.event.observeAsState()
    val users by viewModel.users.observeAsState()
    val sendMessageFailedEvent by viewModel.sendMessageFailedEvent.collectAsState()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        event?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Header(
                    navigator = navigator,
                    event = event!!
                )
                InfoBox(event = event!!)
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.beThereDimens.gapNormal,
                            vertical = MaterialTheme.beThereDimens.gapMedium
                        )
                ) {
                    event?.let {
                        items(event!!.messages) {
                            MessageCard(
                                viewModel = viewModel,
                                messageItem = it,
                                currentUser = currentUser,
                                users = users
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.beThereDimens.gapNormal,
                        end = MaterialTheme.beThereDimens.gapMedium,
                        bottom = MaterialTheme.beThereDimens.gapMedium,
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = messageText,
                    onValueChange = viewModel::onMessageTextChange,
                    placeholder = { Text(stringResource(R.string.message_placeholder)) },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedLabelColor = MaterialTheme.beThereColors.darkGray,
                        textColor = MaterialTheme.beThereColors.black,
                        focusedIndicatorColor = MaterialTheme.beThereColors.transparent,
                        unfocusedIndicatorColor = MaterialTheme.beThereColors.transparent,
                        disabledIndicatorColor = MaterialTheme.beThereColors.transparent,
                        backgroundColor = MaterialTheme.beThereColors.gray,
                        cursorColor = MaterialTheme.beThereColors.black
                    ),
                    textStyle = MaterialTheme.beThereTypography.beThereTextFieldTextStyle,
                    shape = RoundedCornerShape(MaterialTheme.beThereDimens.textFieldCornerSize)
                )
                IconButton(
                    onClick = {
                        viewModel.sendMessage(
                            currentUser = currentUser,
                            event = event!!,
                            text = messageText
                        )
                        coroutineScope.launch {
                            delay(200)
                            listState.animateScrollToItem(event!!.messages.size)
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = null,
                    )
                }
            }
        }
        if (sendMessageFailedEvent.isSendMessageFailed) {
            viewModel.handledSendMessageFailedEvent()
            Toast.makeText(
                LocalContext.current,
                "Error sending a message",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

@Composable
private fun Header(
    navigator: DestinationsNavigator,
    event: Event
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { navigator.popBackStack() },
            modifier = Modifier
                .padding(start = MaterialTheme.beThereDimens.gapMedium)
                .weight(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = null
            )
        }
        Text(
            text = event.name,
            style = MaterialTheme.beThereTypography.eventTitleTextStyle,
            modifier = Modifier.weight(5f)
        )
        IconButton(
            onClick = { navigator.navigate(EventDetailsScreenDestination) },
            modifier = Modifier
                .padding(end = MaterialTheme.beThereDimens.gapMedium)
                .weight(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun InfoBox(event: Event) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        disabledTextColor = MaterialTheme.beThereColors.black,
        disabledIndicatorColor = MaterialTheme.beThereColors.transparent,
        backgroundColor = MaterialTheme.beThereColors.gray,
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
                    value = event.date.toSimpleString(),
                    onValueChange = { },
                    enabled = false,
                    textStyle = MaterialTheme.beThereTypography.descriptionTextStyle,
                    colors = textFieldColors
                )
                TextField(
                    value = event.location,
                    onValueChange = { },
                    enabled = false,
                    textStyle = MaterialTheme.beThereTypography.descriptionTextStyle,
                    colors = textFieldColors
                )
            }
        }
    }
    Divider(
        thickness = MaterialTheme.beThereDimens.dividerThickness,
        color = MaterialTheme.beThereColors.black,
        modifier = Modifier.padding(horizontal = MaterialTheme.beThereDimens.gapNormal)
    )
}

@Composable
private fun MessageCard(
    viewModel: EventViewModel,
    messageItem: Message,
    currentUser: User,
    users: List<User>?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = when (messageItem.sentBy) {
            currentUser.id -> Alignment.End
            else -> Alignment.Start
        },
    ) {
        Card(
            modifier = Modifier.widthIn(max = 340.dp),
            shape = cardShapeFor(messageItem, currentUser.id),
            backgroundColor = when (messageItem.sentBy) {
                currentUser.id -> MaterialTheme.colors.primary
                else -> MaterialTheme.colors.secondary
            },
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = messageItem.text,
                color = when (messageItem.sentBy) {
                    currentUser.id -> MaterialTheme.colors.onPrimary
                    else -> MaterialTheme.colors.onSecondary
                },
            )
        }
        Text(
            text = when (messageItem.sentBy) {
                currentUser.id -> currentUser.name
                else -> viewModel.findUserNameById(id = messageItem.sentBy, users = users)
            },
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun cardShapeFor(message: Message, userId: String): RoundedCornerShape {
    val roundedCorners = RoundedCornerShape(16.dp)
    return when (message.sentBy) {
        userId -> roundedCorners.copy(bottomEnd = CornerSize(0))
        else -> roundedCorners.copy(bottomStart = CornerSize(0))
    }
}
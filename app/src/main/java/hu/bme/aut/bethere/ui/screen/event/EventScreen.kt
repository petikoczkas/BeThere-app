package hu.bme.aut.bethere.ui.screen.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.data.model.Event
import hu.bme.aut.bethere.data.model.Message
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.screen.destinations.EventDetailsScreenDestination
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography
import hu.bme.aut.bethere.ui.view.card.ProfilePicture
import hu.bme.aut.bethere.ui.view.dialog.BeThereAlertDialog
import hu.bme.aut.bethere.utils.toSimpleString

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


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        event?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.beThereDimens.gapVeryLarge)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Header(
                    navigator = navigator,
                    event = event!!,
                    currentUser = currentUser
                )
                InfoBox(event = event!!)
                LazyColumn(
                    reverseLayout = true,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .imePadding()
                        .padding(
                            horizontal = MaterialTheme.beThereDimens.gapNormal,
                            vertical = MaterialTheme.beThereDimens.gapMedium
                        )
                ) {
                    items(event!!.messages.reversed()) {
                        MessageCard(
                            viewModel = viewModel,
                            messageItem = it,
                            currentUser = currentUser,
                            users = users
                        )
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
                OutlinedTextField(
                    value = messageText,
                    onValueChange = viewModel::onMessageTextChange,
                    placeholder = { Text(stringResource(R.string.message_placeholder)) },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = MaterialTheme.colors.secondary,
                        textColor = MaterialTheme.colors.onBackground,
                        leadingIconColor = MaterialTheme.colors.onSecondary,
                        unfocusedBorderColor = MaterialTheme.colors.onSecondary,
                        focusedBorderColor = MaterialTheme.colors.primary,
                        placeholderColor = MaterialTheme.colors.onSecondary,
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
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
        if (sendMessageFailedEvent.isSendMessageFailed) {
            BeThereAlertDialog(
                title = stringResource(R.string.send_message_failed),
                description = sendMessageFailedEvent.exception?.message.toString(),
                onDismiss = { viewModel.handledSendMessageFailedEvent() }
            )
        }
    }
}

@Composable
private fun Header(
    navigator: DestinationsNavigator,
    event: Event,
    currentUser: User
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
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
        Text(
            text = event.name,
            style = MaterialTheme.beThereTypography.titleTextStyle,
            modifier = Modifier.weight(5f)
        )
        IconButton(
            onClick = {
                navigator.navigate(
                    EventDetailsScreenDestination(
                        eventId = event.id,
                        currentUser = currentUser
                    )
                )
            },
            modifier = Modifier
                .padding(end = MaterialTheme.beThereDimens.gapMedium)
                .weight(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
private fun InfoBox(event: Event) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = MaterialTheme.beThereDimens.gapNormal,
                top = MaterialTheme.beThereDimens.gapNormal,
                end = MaterialTheme.beThereDimens.gapNormal,
                bottom = MaterialTheme.beThereDimens.gapMedium
            )
            .clip(RoundedCornerShape(MaterialTheme.beThereDimens.textFieldCornerSize))
            .background(MaterialTheme.colors.secondary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.beThereDimens.gapNormal),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.time_and_date),
                    style = MaterialTheme.beThereTypography.descriptionTextStyle,
                    color = MaterialTheme.colors.onSecondary
                )
                Text(
                    text = stringResource(R.string.location),
                    style = MaterialTheme.beThereTypography.descriptionTextStyle,
                    color = MaterialTheme.colors.onSecondary
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = event.date.toSimpleString(),
                    style = MaterialTheme.beThereTypography.descriptionTextStyle,
                    color = MaterialTheme.colors.onSecondary
                )
                Text(
                    text = event.location,
                    style = MaterialTheme.beThereTypography.descriptionTextStyle,
                    color = MaterialTheme.colors.onSecondary
                )
            }
        }
    }
    Divider(
        thickness = MaterialTheme.beThereDimens.dividerThickness,
        color = MaterialTheme.colors.primary,
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
            .padding(
                horizontal = MaterialTheme.beThereDimens.gapMedium,
                vertical = MaterialTheme.beThereDimens.gapSmall
            ),
        horizontalAlignment = when (messageItem.sentBy) {
            currentUser.id -> Alignment.End
            else -> Alignment.Start
        },
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            if (messageItem.sentBy != currentUser.id) {
                ProfilePicture(
                    photo = viewModel.findUserProfileById(
                        id = messageItem.sentBy,
                        users = users
                    ),
                    modifier = Modifier
                        .padding(end = MaterialTheme.beThereDimens.gapTiny)
                        .size(MaterialTheme.beThereDimens.messageCardImageSize)
                )
            }
            Card(
                modifier = Modifier.widthIn(max = MaterialTheme.beThereDimens.messageCardSize),
                shape = cardShapeFor(messageItem, currentUser.id),
                backgroundColor = when (messageItem.sentBy) {
                    currentUser.id -> MaterialTheme.colors.primary
                    else -> MaterialTheme.colors.secondary
                },
            ) {
                Text(
                    modifier = Modifier.padding(MaterialTheme.beThereDimens.gapMedium),
                    text = messageItem.text,
                    color = when (messageItem.sentBy) {
                        currentUser.id -> MaterialTheme.colors.onPrimary
                        else -> MaterialTheme.colors.onSecondary
                    },
                    style = MaterialTheme.beThereTypography.messageTextStyle
                )
            }
            if (messageItem.sentBy == currentUser.id) {
                ProfilePicture(
                    photo = currentUser.photo,
                    modifier = Modifier
                        .padding(start = MaterialTheme.beThereDimens.gapTiny)
                        .size(MaterialTheme.beThereDimens.messageCardImageSize)
                )
            }
        }
        Text(
            text = when (messageItem.sentBy) {
                currentUser.id -> currentUser.name
                else -> viewModel.findUserNameById(id = messageItem.sentBy, users = users)
            },
            style = MaterialTheme.beThereTypography.messageOwnerTextStyle
        )
    }
}

@Composable
private fun cardShapeFor(message: Message, userId: String): RoundedCornerShape {
    val roundedCorners = RoundedCornerShape(MaterialTheme.beThereDimens.messageCardCornerSize)
    return when (message.sentBy) {
        userId -> roundedCorners.copy(bottomEnd = CornerSize(0))
        else -> roundedCorners.copy(bottomStart = CornerSize(0))
    }
}
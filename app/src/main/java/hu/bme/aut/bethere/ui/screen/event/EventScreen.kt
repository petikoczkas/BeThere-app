package hu.bme.aut.bethere.ui.screen.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.theme.beThereColors
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography

@Composable
fun EventScreen() {
    var text by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Header()
            InfoBox()
            TODO("Implement chat")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.beThereDimens.gapNormal,
                    top = MaterialTheme.beThereDimens.gapNormal,
                    end = MaterialTheme.beThereDimens.gapMedium,
                    bottom = MaterialTheme.beThereDimens.gapLarge,
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
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
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { /*TODO*/ },
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
            text = "Name of event",
            style = MaterialTheme.beThereTypography.eventTitleTextStyle,
            modifier = Modifier.weight(5f)
        )
        IconButton(
            onClick = { /*TODO*/ },
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
private fun InfoBox() {
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
                    value = "2023.03.12",
                    onValueChange = { },
                    enabled = false,
                    textStyle = MaterialTheme.beThereTypography.descriptionTextStyle,
                    colors = textFieldColors
                )
                TextField(
                    value = "anyu",
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
package hu.bme.aut.bethere.ui.screen.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography
import hu.bme.aut.bethere.ui.view.card.UserCard
import hu.bme.aut.bethere.ui.view.textfield.SearchField

@Composable
fun SearchScreen() {
    var search by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = MaterialTheme.beThereDimens.gapMedium)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
            Text(
                text = stringResource(R.string.search),
                style = MaterialTheme.beThereTypography.titleTextStyle,
                modifier = Modifier.padding(
                    top = MaterialTheme.beThereDimens.gapMedium,
                    bottom = MaterialTheme.beThereDimens.gapVeryVeryLarge,
                )
            )
            SearchField(text = search, onTextChange = { search = it }, onSearchButtonClick = {})
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.beThereDimens.userBoxHeight)
                    .padding(top = MaterialTheme.beThereDimens.gapNormal)
            ) {
                Text(
                    text = stringResource(R.string.your_friends),
                    style = MaterialTheme.beThereTypography.columnDescriptionTextStyle,
                    modifier = Modifier.padding(
                        start = MaterialTheme.beThereDimens.gapNormal,
                    )
                )
                LazyColumn(
                    modifier = Modifier
                        .padding(
                            start = MaterialTheme.beThereDimens.gapNormal,
                            top = MaterialTheme.beThereDimens.gapLarge,
                            end = MaterialTheme.beThereDimens.gapNormal,
                        )
                ) {
                    items(5) {
                        UserCard(
                            text = "Name of client",
                            onClick = {},
                            addButtonOnClick = {},
                            modifier = Modifier
                                .padding(vertical = MaterialTheme.beThereDimens.gapSmall)
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.beThereDimens.userBoxHeight)
                    .padding(top = MaterialTheme.beThereDimens.gapNormal)
            ) {
                Text(
                    text = stringResource(R.string.others),
                    style = MaterialTheme.beThereTypography.columnDescriptionTextStyle,
                    modifier = Modifier.padding(
                        start = MaterialTheme.beThereDimens.gapNormal,
                    )
                )
                LazyColumn(
                    modifier = Modifier
                        .padding(
                            start = MaterialTheme.beThereDimens.gapNormal,
                            top = MaterialTheme.beThereDimens.gapLarge,
                            end = MaterialTheme.beThereDimens.gapNormal,
                        )
                ) {
                    items(5) {
                        UserCard(
                            text = "Name of client",
                            onClick = {},
                            addButtonOnClick = {},
                            modifier = Modifier
                                .padding(vertical = MaterialTheme.beThereDimens.gapSmall)
                        )
                    }
                }
            }
        }
    }
}
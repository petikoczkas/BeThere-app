package hu.bme.aut.bethere.ui.view.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.theme.beThereColors
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography

@Composable
fun UserCard(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(MaterialTheme.beThereDimens.cardCornerSize))
            .heightIn(MaterialTheme.beThereDimens.minUserCardHeight)
            .clickable { onClick() },
        backgroundColor = MaterialTheme.beThereColors.gray,
        shape = RoundedCornerShape(MaterialTheme.beThereDimens.cardCornerSize)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = MaterialTheme.beThereDimens.gapMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_account_circle),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = MaterialTheme.beThereDimens.gapMedium)
                )
                Text(text = text, style = MaterialTheme.beThereTypography.cardTextStyle)
            }
            content()
        }
    }
}
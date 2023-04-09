package hu.bme.aut.bethere.ui.view.card

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography

@Composable
fun UserCard(
    text: String,
    photo: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(MaterialTheme.beThereDimens.cardCornerSize))
            .heightIn(MaterialTheme.beThereDimens.minUserCardHeight),
        backgroundColor = MaterialTheme.colors.secondary,
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
                ProfilePicture(
                    photo = photo,
                    modifier = Modifier
                        .padding(end = MaterialTheme.beThereDimens.gapMedium)
                        .size(MaterialTheme.beThereDimens.userCardImageSize)
                )
                Text(text = text, style = MaterialTheme.beThereTypography.cardTextStyle)
            }
            content()
        }
    }
}
package hu.bme.aut.bethere.ui.view.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
fun EventCard(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(MaterialTheme.beThereDimens.cardCornerSize))
            .heightIn(MaterialTheme.beThereDimens.minEventCardHeight)
            .clickable { onClick() },
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(MaterialTheme.beThereDimens.cardCornerSize)
    ) {
        Row(
            modifier = Modifier.padding(start = MaterialTheme.beThereDimens.gapNormal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, style = MaterialTheme.beThereTypography.cardTextStyle)
        }
    }
}
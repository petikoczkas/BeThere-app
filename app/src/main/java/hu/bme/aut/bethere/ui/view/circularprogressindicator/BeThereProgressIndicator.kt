package hu.bme.aut.bethere.ui.view.circularprogressindicator

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import hu.bme.aut.bethere.ui.theme.beThereDimens

@Composable
fun BeThereProgressIndicator(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxHeight()
            .wrapContentSize(align = Alignment.Center)
            .then(Modifier.size(MaterialTheme.beThereDimens.circularProgressIndicatorSize))
    )
}

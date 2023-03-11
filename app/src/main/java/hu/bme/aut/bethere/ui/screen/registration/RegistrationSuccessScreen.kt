package hu.bme.aut.bethere.ui.screen.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import hu.bme.aut.bethere.ui.view.button.PrimaryButton

@Composable
fun RegistrationSuccessScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.beThereDimens.gapNormal),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Successful Registration",
                style = MaterialTheme.beThereTypography.titleTextStyle,
                modifier = Modifier.padding(
                    top = MaterialTheme.beThereDimens.gapVeryVeryLarge,
                    bottom = MaterialTheme.beThereDimens.gapLarge
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.beThereDimens.gapLarge)
                    .height(420.dp)
                    .clip(RoundedCornerShape(MaterialTheme.beThereDimens.primaryButtonCornerSize))
                    .background(MaterialTheme.beThereColors.gray),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_done),
                    contentDescription = null,
                    modifier = Modifier
                        .size(380.dp)
                        .align(Alignment.Center)
                )
            }
        }
        PrimaryButton(
            onClick = { /*TODO*/ },
            text = "Next",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.beThereDimens.gapLarge)
        )
    }
}
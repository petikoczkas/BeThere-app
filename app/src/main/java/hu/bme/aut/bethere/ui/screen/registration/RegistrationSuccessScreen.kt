package hu.bme.aut.bethere.ui.screen.registration

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.screen.destinations.HomeScreenDestination
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography
import hu.bme.aut.bethere.ui.view.button.PrimaryButton

@Destination
@Composable
fun RegistrationSuccessScreen(navigator: DestinationsNavigator) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.successful_registration)
    )

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false

    )

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
                text = stringResource(R.string.succesful_registration),
                style = MaterialTheme.beThereTypography.titleTextStyle,
                modifier = Modifier.padding(
                    top = MaterialTheme.beThereDimens.gapVeryLarge,
                    bottom = MaterialTheme.beThereDimens.gapLarge
                )
            )
            LottieAnimation(
                composition,
                progress,
                modifier = Modifier
                    .size(420.dp)
                    .padding(vertical = MaterialTheme.beThereDimens.gapLarge)
            )
        }
        PrimaryButton(
            onClick = { navigator.navigate(HomeScreenDestination) },
            text = stringResource(R.string.next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.beThereDimens.gapLarge)
        )
    }
}
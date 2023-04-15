package hu.bme.aut.bethere.ui.view.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import hu.bme.aut.bethere.R

@Composable
fun ProfilePicture(
    photo: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = CircleShape,
        backgroundColor = MaterialTheme.colors.primary,
        modifier = modifier
    ) {
        if (photo.isEmpty()) {
            Icon(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = null,
                tint = MaterialTheme.colors.background,
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(model = photo),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}
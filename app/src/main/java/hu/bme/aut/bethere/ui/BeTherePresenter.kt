package hu.bme.aut.bethere.ui

import android.net.Uri
import hu.bme.aut.bethere.data.BeThereInteractor
import hu.bme.aut.bethere.data.model.Event
import hu.bme.aut.bethere.data.model.User
import javax.inject.Inject
class BeTherePresenter @Inject constructor(
    private val beThereInteractor: BeThereInteractor
) {

    suspend fun registrate(
        email: String,
        password: String,
        user: User,
        onSuccess: () -> Unit,
        onFailure: (Exception?) -> Unit
    ) {
        beThereInteractor.registrate(
            email = email,
            password = password,
            user = user,
            onSuccess = {
                onSuccess()
            },
            onFailure = {
                onFailure(it)
            }
        )
    }

    suspend fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception?) -> Unit
    ) {
        beThereInteractor.signIn(
            email = email,
            password = password,
            onSuccess = {
                onSuccess()
            },
            onFailure = {
                onFailure(it)
            }
        )
    }

    fun signOut() = beThereInteractor.signOut()

    fun getUsers() = beThereInteractor.getUsers()

    suspend fun getCurrentUser() = beThereInteractor.getCurrentUser()

    suspend fun updateUser(user: User) {
        beThereInteractor.updateUser(user = user)
    }

    fun getCurrentUserEvents(user: User) = beThereInteractor.getCurrentUserEvents(user = user)

    fun getCurrentEvent(eventId: String) = beThereInteractor.getCurrentEvent(eventId = eventId)

    suspend fun updateEvent(event: Event) {
        beThereInteractor.updateEvent(event = event)
    }

    suspend fun uploadProfilePicture(userId: String, imageUri: Uri, onSuccess: (String) -> Unit) {
        beThereInteractor.uploadProfilePicture(
            userId = userId,
            imageUri = imageUri,
            onSuccess = onSuccess
        )
    }

}


package hu.bme.aut.bethere.ui

import hu.bme.aut.bethere.data.BeThereInteractor
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

    suspend fun getUsers() = beThereInteractor.getUsers()

    //suspend fun getCurrentUser() = beThereInteractor.getCurrentUser()


}

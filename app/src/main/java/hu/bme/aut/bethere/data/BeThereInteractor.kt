package hu.bme.aut.bethere.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.service.FirebaseAuthService
import hu.bme.aut.bethere.service.FirebaseStorageService
import hu.bme.aut.bethere.utils.Constants.NAME_PROPERTY
import hu.bme.aut.bethere.utils.Constants.USER_COLLECTION
import javax.inject.Inject

class BeThereInteractor @Inject constructor(
    private val firebaseStorageService: FirebaseStorageService
) {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private var currentUser = firebaseAuth.currentUser

    private val queryUsers = firebaseFirestore.collection(USER_COLLECTION)

    suspend fun registrate(
        email: String,
        password: String,
        user: User,
        onSuccess: () -> Unit,
        onFailure: (Exception?) -> Unit
    ) {
        var userWithID = user
        FirebaseAuthService.registrate(
            firebaseAuth = firebaseAuth,
            email = email,
            password = password,
            onSuccess = {
                onSuccess()
                currentUser = firebaseAuth.currentUser
                userWithID = user.copy(id = currentUser?.uid.toString())
            },
            onFailure = {
                onFailure(it)
            }
        )
        firebaseStorageService.createUser(
            firebaseFirestore = firebaseFirestore,
            user = userWithID,
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
        FirebaseAuthService.signIn(
            firebaseAuth = firebaseAuth,
            email = email,
            password = password,
            onSuccess = {
                onSuccess()
                currentUser = firebaseAuth.currentUser
            },
            onFailure = {
                onFailure(it)
            }
        )
    }

    suspend fun getUsers(): DataOrException<MutableList<User>, Exception> {
        return firebaseStorageService.getUsers(
            queryUsersByName = queryUsers.orderBy(NAME_PROPERTY, Query.Direction.ASCENDING),
            userId = currentUser?.uid.toString()
        )
    }

    suspend fun updateUser(user: User) {
        firebaseStorageService.updateUser(
            firebaseFirestore = firebaseFirestore,
            user = user,
        )
    }
}



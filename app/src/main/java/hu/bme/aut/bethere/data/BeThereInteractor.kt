package hu.bme.aut.bethere.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import hu.bme.aut.bethere.data.model.Event
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.service.FirebaseAuthService
import hu.bme.aut.bethere.service.FirebaseStorageService
import hu.bme.aut.bethere.utils.Constants.NAME_PROPERTY
import hu.bme.aut.bethere.utils.Constants.USER_COLLECTION
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BeThereInteractor @Inject constructor(
    private val firebaseStorageService: FirebaseStorageService
) {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private var currentUser = firebaseAuth.currentUser

    private val queryUsers = firebaseFirestore.collection(USER_COLLECTION)

    var selectedUsersOnSearchScreen = mutableListOf<User>()

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

    fun signOut() {
        FirebaseAuthService.signOut(firebaseAuth = firebaseAuth)
    }

    fun getUsers(): Flow<List<User>> {
        return firebaseStorageService.getUsers(
            queryUsersByName = queryUsers.orderBy(NAME_PROPERTY, Query.Direction.ASCENDING),
            userId = currentUser?.uid.toString()
        )
    }

    suspend fun getCurrentUser() = firebaseStorageService.getCurrentUser(
        queryUsers = queryUsers,
        userId = currentUser?.uid.toString()
    )

    suspend fun updateUser(user: User) {
        firebaseStorageService.updateUser(
            firebaseFirestore = firebaseFirestore,
            user = user,
        )
    }

    fun getCurrentUserEvents(user: User) =
        firebaseStorageService.getCurrentUserEvents(
            firebaseFirestore = firebaseFirestore,
            user = user
        )

    fun getCurrentEvent(eventId: String) =
        firebaseStorageService.getCurrentEvent(
            firebaseFirestore = firebaseFirestore,
            eventId = eventId
        )

    suspend fun updateEvent(event: Event) {
        firebaseStorageService.updateEvent(
            firebaseFirestore = firebaseFirestore,
            event = event
        )
    }

}



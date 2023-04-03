package hu.bme.aut.bethere.service

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import hu.bme.aut.bethere.data.model.Event
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.utils.Constants.EVENT_COLLECTION
import hu.bme.aut.bethere.utils.Constants.USER_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseStorageService @Inject constructor() {

    suspend fun createUser(
        firebaseFirestore: FirebaseFirestore,
        user: User,
        onSuccess: () -> Unit,
        onFailure: (Exception?) -> Unit
    ) {
        firebaseFirestore.collection(USER_COLLECTION).document(user.id).set(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(it.exception)
                }
            }.await()
    }


    fun getUsers(
        queryUsersByName: Query,
        userId: String
    ): Flow<List<User>> {
        return callbackFlow {
            val listener = queryUsersByName.addSnapshotListener { value, e ->
                e?.let {
                    return@addSnapshotListener
                }
                value?.let {
                    val tmp = mutableListOf<User>()
                    for (d in it.documents) {
                        if (d.id != userId) d.toObject(User::class.java)
                            ?.let { doc -> tmp.add(doc) }
                    }
                    trySend(tmp.toList())
                }
            }
            awaitClose {
                listener.remove()
            }
        }
    }

    suspend fun getCurrentUser(
        queryUsers: Query,
        userId: String
    ): User {
        var currentUser = User()
        queryUsers.get().await().map { document ->
            if (document.id == userId) currentUser = document.toObject(User::class.java)
        }
        return currentUser
    }

    suspend fun updateUser(
        firebaseFirestore: FirebaseFirestore,
        user: User,
    ) {
        firebaseFirestore.collection(USER_COLLECTION).document(user.id).set(user).await()
    }

    fun getCurrentUserEvents(
        firebaseFirestore: FirebaseFirestore,
        user: User
    ): Flow<List<Event>> {
        return callbackFlow {
            val listener =
                firebaseFirestore.collection(EVENT_COLLECTION).addSnapshotListener { value, e ->
                    e?.let {
                        return@addSnapshotListener
                    }
                    value?.let {
                        val tmp = mutableListOf<Event>()
                        for (d in it.documents) {
                            for (eId in user.events) {
                                if (eId == d.id) {
                                    d.toObject(Event::class.java)?.let { doc ->
                                        tmp.add(doc)
                                    }
                                }
                            }
                        }
                        trySend(tmp.toList())
                    }
                }
            awaitClose {
                listener.remove()
            }
        }
    }

    fun getCurrentEvent(
        firebaseFirestore: FirebaseFirestore,
        eventId: String
    ): Flow<Event> {
        return callbackFlow {
            val listener =
                firebaseFirestore.collection(EVENT_COLLECTION).addSnapshotListener { value, e ->
                    e?.let {
                        return@addSnapshotListener
                    }
                    value?.let {
                        for (d in it.documents) {
                            if (d.id == eventId) d.toObject(Event::class.java)
                                ?.let { doc -> trySend(doc) }
                        }
                    }
                }
            awaitClose {
                listener.remove()
            }
        }
    }

    suspend fun updateEvent(
        firebaseFirestore: FirebaseFirestore,
        event: Event,
    ) {
        if (event.users.isEmpty()) firebaseFirestore.collection(EVENT_COLLECTION).document(event.id)
            .delete().await()
        else firebaseFirestore.collection(EVENT_COLLECTION).document(event.id).set(event).await()
    }

    suspend fun uploadProfilePicture(
        firebaseStorage: StorageReference,
        userId: String,
        imageUri: Uri,
        onSuccess: (String) -> Unit,
    ) {
        val metadata = StorageMetadata.Builder()
            .setContentType("image/jpeg")
            .build()

        firebaseStorage.child(userId).putFile(imageUri, metadata)
            .addOnSuccessListener { taskSnapshot ->
                val result = taskSnapshot.metadata?.reference?.downloadUrl
                result?.addOnSuccessListener {
                    onSuccess(it.toString())
                }
            }.await()
    }
}
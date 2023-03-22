package hu.bme.aut.bethere.service

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import hu.bme.aut.bethere.data.DataOrException
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.utils.Constants.USER_COLLECTION
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


    suspend fun getUsers(
        queryUsersByName: Query,
        userId: String
    ): DataOrException<MutableList<User>, Exception> {
        val dataOrException = DataOrException<MutableList<User>, Exception>()
        val tmp = mutableListOf<User>()
        var currentUser = User()
        try {
            queryUsersByName.get().await().map { document ->
                if (document.id != userId) tmp.add(document.toObject(User::class.java))
                else currentUser = document.toObject(User::class.java)
            }
            tmp.add(currentUser)
            dataOrException.data = tmp
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }
}
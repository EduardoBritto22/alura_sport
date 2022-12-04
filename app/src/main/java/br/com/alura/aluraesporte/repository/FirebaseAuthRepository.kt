package br.com.alura.aluraesporte.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*

private const val TAG = "FirebaseAuthRepository"

class FirebaseAuthRepository(private val firebaseAuth: FirebaseAuth) {

    fun logoutUser(firebaseAuth: FirebaseAuth) {
        firebaseAuth.signOut()
    }

    fun verifyUser(firebaseAuth: FirebaseAuth) {
        val currentUser: FirebaseUser? = firebaseAuth.currentUser
        if (currentUser != null) {
        } else {
        }
    }

    fun authenticateUser(firebaseAuth: FirebaseAuth) {
        firebaseAuth.signInWithEmailAndPassword(
            "alex@aluraesporte.com",
            "teste123"
        ).addOnSuccessListener {

        }.addOnFailureListener {

        }
    }

    fun registerUser(email: String, password: String): LiveData<Resource<Boolean>> {
        val liveData = MutableLiveData<Resource<Boolean>>()
        val task: Task<AuthResult> =
            firebaseAuth.createUserWithEmailAndPassword(email, password)

        task.addOnSuccessListener {
            Log.i(TAG, "registerUser: Success")
            liveData.value = Resource(true)
        }

        task.addOnFailureListener {exception->
            Log.i(TAG, "registerUser: Failure", exception)
            val errorMessage = when(exception){
                is FirebaseAuthWeakPasswordException -> "The password must have at least 6 digits"
                is FirebaseAuthInvalidCredentialsException -> "Invalid email"
                is FirebaseAuthUserCollisionException -> "Email already registered"
                else -> "Unknown error"
            }
            liveData.value = Resource(false, errorMessage)
        }

        return liveData
    }


}
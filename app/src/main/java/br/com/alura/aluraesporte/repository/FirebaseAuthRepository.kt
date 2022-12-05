package br.com.alura.aluraesporte.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.aluraesporte.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import java.lang.Exception

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

    fun registerUser(user: User): LiveData<Resource<Boolean>> {
        val liveData = MutableLiveData<Resource<Boolean>>()
        try {
            val task: Task<AuthResult> =
                firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)

            task.addOnSuccessListener {
                Log.i(TAG, "registerUser: Success")
                liveData.value = Resource(true)
            }

            task.addOnFailureListener { exception ->
                Log.i(TAG, "registerUser: Failure", exception)
                val errorMessage = getRegisterError(exception)
                liveData.value = Resource(false, errorMessage)
            }
        } catch (e: IllegalArgumentException) {
            liveData.value = Resource(false, "E-mail or password can not be empty")
        }

        return liveData
    }

    private fun getRegisterError(exception: Exception): String {
        val errorMessage = when (exception) {
            is FirebaseAuthWeakPasswordException -> "The password must have at least 6 digits"
            is FirebaseAuthInvalidCredentialsException -> "Invalid email"
            is FirebaseAuthUserCollisionException -> "Email already registered"
            else -> "Unknown error"
        }
        return errorMessage
    }


}
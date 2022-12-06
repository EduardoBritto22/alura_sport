package br.com.alura.aluraesporte.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.aluraesporte.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*

private const val TAG = "FirebaseAuthRepository"

class FirebaseAuthRepository(private val firebaseAuth: FirebaseAuth) {

    fun logOut() {
        firebaseAuth.signOut()
    }

    fun authenticate(user: User): LiveData<Resource<Boolean>> {
        val liveData = MutableLiveData<Resource<Boolean>>()
        try {
            firebaseAuth.signInWithEmailAndPassword(
                user.email,
                user.password
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    liveData.value = Resource(true)
                } else {
                    Log.e(TAG, "authenticate: ", task.exception)
                    val errorMessage = getAuthenticateError(task.exception)
                    liveData.value = Resource(false,errorMessage)
                }
            }
        } catch (e: Exception) {
            liveData.value = Resource(false, "E-mail or password can not be empty")
        }
        return liveData
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
            is FirebaseAuthInvalidCredentialsException -> "You have entered an invalid email or password"
            is FirebaseAuthUserCollisionException -> "Email already registered"
            else -> "Unknown error"
        }
        return errorMessage
    }

    private fun getAuthenticateError(exception: Exception?): String {
        val errorMessage = when (exception) {
            is FirebaseAuthInvalidUserException,
            is FirebaseAuthInvalidCredentialsException -> "You have entered an invalid email or password"
            else -> "Unknown error"
        }
        return errorMessage
    }

    fun isLogged(): Boolean {
        val currentUser: FirebaseUser? = firebaseAuth.currentUser
        if (currentUser != null) {
            return true
        }
        return false
    }


}
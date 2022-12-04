package br.com.alura.aluraesporte.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private const val TAG = "FirebaseAuthRepository"

class FirebaseAuthRepository(private val firebaseAuth: FirebaseAuth) {

    public fun logoutUser(firebaseAuth: FirebaseAuth) {
        firebaseAuth.signOut()
    }

    public fun verifyUser(firebaseAuth: FirebaseAuth) {
        val currentUser: FirebaseUser? = firebaseAuth.currentUser
        if (currentUser != null) {
        } else {
        }
    }

    public fun authenticateUser(firebaseAuth: FirebaseAuth) {
        firebaseAuth.signInWithEmailAndPassword(
            "alex@aluraesporte.com",
            "teste123"
        ).addOnSuccessListener {

        }.addOnFailureListener {

        }
    }

    public fun registerUser(email: String, password: String): LiveData<Resource<Boolean>> {
        val liveData = MutableLiveData<Resource<Boolean>>()
        val task: Task<AuthResult> =
            firebaseAuth.createUserWithEmailAndPassword(email, password)

        task.addOnSuccessListener {
            Log.i(TAG, "registerUser: Success")
            liveData.value = Resource(true)
        }

        task.addOnFailureListener {
            Log.i(TAG, "registerUser: Failure", it)
            liveData.value = Resource(false,"Failure to register the user")
        }

        return liveData
    }


}
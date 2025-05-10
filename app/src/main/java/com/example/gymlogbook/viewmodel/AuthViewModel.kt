package com.example.gymlogbook.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.gymlogbook.extensions.StringExtensions.isValidEmail
import com.example.gymlogbook.extensions.StringExtensions.isValidPassword
import com.example.gymlogbook.model.UserData
import com.example.gymlogbook.services.Constants.USERS
import com.example.gymlogbook.services.Helper.showSnackbar
import com.example.gymlogbook.services.Helper.showToast
import com.example.gymlogbook.services.LoadingManager.hideLoading
import com.example.gymlogbook.services.LoadingManager.showLoading
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore
) : ViewModel() {

    val signedIn = mutableStateOf(false)

    init {
        showLoading()
        val currentUser = auth.currentUser
        signedIn.value = currentUser != null
        hideLoading()
    }


    fun loginBtnClick(email: String, password: String) {
        val validCredentials: String? = validEmailAndPassword(email, password);
        if (validCredentials != null) {
            showSnackbar(validCredentials)
            return
        }
        showLoading()
        // check if user exists,
        // if exists, proceed to login
        // if not, then create user and then proceed to login
        checkUserExists(email, password)
    }

    private fun checkUserExists(email: String, password: String) {
        db.collection(USERS).whereEqualTo("email", email).get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    showToast("User does not exists")
                    // create user and then login
                    createUserWithCredentials(email, password)
                } else {
                    showToast("User already exists with this email")
                    // proceed directly to login
                    login(email, password)
                }
            }
            .addOnFailureListener {
                showToast("something went wrong: ${it.localizedMessage}")
                hideLoading()
            }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                showToast("Login successful")
                hideLoading()
                // navigate to home screen
                signedIn.value = true // this will auto navigate to home screen
                // observing on start of the auth screen composable
            }.addOnFailureListener {
                showToast("Login failed: ${it.localizedMessage}")
                hideLoading()
            }
    }


    private fun createUserWithCredentials(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                createUserDataInFirestore(email, password)
            }
            .addOnFailureListener {
                showToast("something went wrong: ${it.localizedMessage}")
                hideLoading()
            }
    }

    private fun createUserDataInFirestore(email: String, password: String) {
        val uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            email = email,
        )
        uid?.let {
            // using same document ID
//            db.collection(USERS).document(uid).collection(INFO).document(uid).set(userData)
            db.collection(USERS).document(uid).set(userData)
                .addOnSuccessListener {
                    // auth user create, firestore data created, proceed to login
                    login(email, password)
                }
                .addOnFailureListener {
                    showToast("something went wrong: ${it.localizedMessage}")
                    hideLoading()
                }
        }
    }

    fun validEmailAndPassword(email: String?, password: String?): String? {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            return "Credentials cannot be empty"
        } else if (!(email.isValidEmail()) || !(password.isValidPassword())) {
            return "Enter valid credentials"
        }
        // else
        return null;
    }
}
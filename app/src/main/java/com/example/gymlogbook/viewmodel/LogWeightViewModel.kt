package com.example.gymlogbook.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.gymlogbook.model.WeightData
import com.example.gymlogbook.services.Constants.USERS
import com.example.gymlogbook.services.Constants.WEIGHTS
import com.example.gymlogbook.services.Helper.showToast
import com.example.gymlogbook.services.LoadingManager.hideLoading
import com.example.gymlogbook.services.LoadingManager.showLoading
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LogWeightViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore
) : ViewModel() {

    private val _weightsLog = mutableStateListOf<WeightData>()
    val weightsLog: List<WeightData> get() = _weightsLog

    val loggedWeightData = mutableStateOf<WeightData?>(null)
    val isAlreadyLoggedToday = mutableStateOf(false)

    init {
        getWeightLogs()
    }

    // get list of weight logged so far
    // check latest log is today
    // if yes, update
    // if no, add new
    // if no logged anything, then set new weight log

    fun onSubmitButtonClick(weight: Double) {
        if (loggedWeightData.value != null) {
            showToast("You have already logged today, editing today weight log")
            updateWeight(weight)
        } else {
            showToast("Logging new weight")
            logWeight(weight)
        }
    }


    private fun updateWeight(weight: Double) {
        val weightDocId = loggedWeightData.value?.docId
        weightDocId?.let {
            logWeight(weight, weightDocId)
        }
    }

    private fun logWeight(weight: Double, docId: String? = null) {
        val uid = auth.currentUser?.uid ?: return
        showLoading()

        val timestamp = Timestamp.now()

        val randomDocId = UUID.randomUUID().toString()
        val documentId = docId ?: randomDocId

        val weightData = WeightData(
            weight = weight,
            timestamp = loggedWeightData.value?.timestamp ?: timestamp,
            docId = documentId
        )

        db.collection(USERS).document(uid).collection(WEIGHTS)
            // if docId is null then its new logging
            // if not then just updating the values
            .document(documentId)
            .set(weightData)
            .addOnSuccessListener {
                showToast("weight logged successfully")
                getWeightLogs()
            }
            .addOnFailureListener {
                showToast("error logging weight: ${it.localizedMessage}")
                hideLoading()
            }
    }

    fun deleteWeightLog(weightData: WeightData) {
        val uid = auth.currentUser?.uid ?: return
        showLoading()
        db.collection(USERS).document(uid).collection(WEIGHTS).document(weightData.docId!!).delete()
            .addOnSuccessListener {
                showToast("weight logged deleted successfully")
                getWeightLogs()
            }
            .addOnFailureListener {
                showToast("error deleting weight log: ${it.localizedMessage}")
                hideLoading()
            }
    }

    private fun getWeightLogs() {
        val uid = auth.currentUser?.uid ?: return
        showLoading()

        db.collection(USERS)
            .document(uid)
            .collection(WEIGHTS)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { weights ->
                val docs = weights.toObjects(WeightData::class.java).toList()

                // change to default values
                clearValues()

                // update values
                _weightsLog.addAll(docs)

                if (_weightsLog.isNotEmpty()) {
                    val alreadyLoggedToday = isCurrentDate(docs.first())
                    isAlreadyLoggedToday.value = alreadyLoggedToday
                    if (isAlreadyLoggedToday.value) {
                        // first one will be today
                        loggedWeightData.value = docs.firstOrNull()
                    } else {
                        loggedWeightData.value = null
                    }
                } else {
                    // no docs found
                    // not logged for the day
                    isAlreadyLoggedToday.value = false
                    loggedWeightData.value = null
                }
                hideLoading()
            }
            .addOnFailureListener {
                showToast("Error fetching weights: ${it.localizedMessage}")
                hideLoading()
            }
    }

    private fun isCurrentDate(latestWeightLog: WeightData): Boolean {
        latestWeightLog.timestamp?.let {
            val isFromToday = isToday(latestWeightLog.timestamp)
            return isFromToday
        }
        return false
    }

    fun isToday(timestamp: Timestamp): Boolean {
        // Convert the Timestamp to a Date
        val calendarTimestamp = Calendar.getInstance().apply { time = timestamp.toDate() }

        // Get today's date as Calendar object
        val today = Calendar.getInstance()

        // Compare the year, month, and day
        return calendarTimestamp.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                calendarTimestamp.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                calendarTimestamp.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
    }

    private fun clearValues() {
        _weightsLog.clear()
        loggedWeightData.value = null
        isAlreadyLoggedToday.value = false
    }
}
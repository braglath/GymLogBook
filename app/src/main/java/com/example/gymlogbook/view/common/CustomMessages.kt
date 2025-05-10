package com.example.gymlogbook.view.common

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun HandleException(exception: Exception? = null, customMessage: String = "") {
    exception?.printStackTrace()
    val errorMsg = exception?.localizedMessage ?: ""
    val message = if (customMessage.isEmpty()) errorMsg else "$customMessage: $errorMsg"
    Log.e("Exception", message)
    Toast.makeText(LocalContext.current, message, Toast.LENGTH_LONG).show()
}

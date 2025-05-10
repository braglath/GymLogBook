package com.example.gymlogbook.extensions

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

object StringExtensions {
    fun String.isValidEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    fun String.isValidPassword(): Boolean {
        // Example: At least 8 characters, with a number and letter
        val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
        return regex.matches(this)
    }

    fun Timestamp.formatToDate(): String {
        val date = this.toDate()
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return formatter.format(date)
    }
}
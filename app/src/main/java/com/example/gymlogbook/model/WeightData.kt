package com.example.gymlogbook.model

import com.google.firebase.Timestamp

data class WeightData(
    val weight: Double? = null,
    val timestamp: Timestamp? = null,
    val docId: String? = null
) {
    fun toMap() = mapOf(
        "weight" to weight,
        "date" to timestamp,
        "docId" to docId
    )
}

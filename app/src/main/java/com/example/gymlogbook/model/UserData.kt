package com.example.gymlogbook.model


data class UserData(
    val userId: String? = null,
    val email: String? = null,
    val name: String? = null,
    val imageUrl: String? = null,
) {
    // conv this data type to a map, so it can be store in firebase
    fun toMap() = mapOf(
        "userId" to userId,
        "name" to name,
        "imageUrl" to imageUrl,
    )
}

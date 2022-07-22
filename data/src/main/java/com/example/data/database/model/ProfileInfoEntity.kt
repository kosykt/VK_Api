package com.example.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProfileInfoEntity(
    @PrimaryKey
    val id: Int,
    val firstName: String,
    val lastName: String,
    val status: String,
    val bdate: String,
)

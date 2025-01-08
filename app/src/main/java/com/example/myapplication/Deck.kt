package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Deck")
data class Deck(
    @PrimaryKey val name: String,
    val imageUri: String,
    val imageUriBack: String?,
    var quantity: Int = 0
)
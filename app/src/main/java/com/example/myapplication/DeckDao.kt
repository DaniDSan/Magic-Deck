package com.example.myapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DeckDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(deck: Deck)

    @Update
    suspend fun updateCard(deck: Deck)

    @Delete
    suspend fun deleteCard(deck: Deck)

    @Query("SELECT * FROM Deck")
    suspend fun getAllCards(): List<Deck>
}
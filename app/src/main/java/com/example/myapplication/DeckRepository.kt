package com.example.myapplication

import androidx.lifecycle.LiveData

class DeckRepository(private val deckDao: DeckDao) {
    val cardViewModel = CardViewModel(this) // Esto es una guarreria ver como hacerlo bien
    suspend fun insertCard(cardData: CardData) {
        val card = convertToDeck(cardData)
        deckDao.insertCard(card)
    }

    suspend fun updateCard(cardData: CardData) {
        val card = convertToDeck(cardData)
        deckDao.updateCard(card)
    }

    suspend fun deleteCard(cardData: CardData) {
        val card = convertToDeck(cardData)
        deckDao.deleteCard(card)
    }

    suspend fun getAllCards(): MutableList<Deck> {
        return deckDao.getAllCards()
    }

    fun deckSize(): LiveData<Int> {
        return deckDao.getDeckSize()
    }

    private fun convertToDeck(cardData: CardData): Deck {
        val card = Deck(
            name = cardData.name,
            imageUri = cardViewModel.getCardImage(cardData), // Esto es una guarreria ver como hacerlo bien, tiene que ver con que necesito coger la imagen segun si tiene cara o no
            imageUriBack = cardData.card_faces?.get(1)?.image_uris?.normal,
            quantity = cardData.cardQuantity
        )
        return card
    }
}
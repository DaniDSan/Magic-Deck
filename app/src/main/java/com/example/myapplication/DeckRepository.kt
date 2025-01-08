package com.example.myapplication

class DeckRepository(private val deckDao: DeckDao) {
    suspend fun insertCard(cardData: CardData) {
        val card = Deck(
            name = cardData.name,
            imageUri = cardData.image_uris.normal,
            imageUriBack = cardData.card_faces?.get(1)?.image_uris?.normal,
            quantity = cardData.cardQuantity
        )
        deckDao.insertCard(card)
    }

    suspend fun updateCard(deck: Deck) {
        deckDao.updateCard(deck)
    }

    suspend fun deleteCard(deck: Deck) {
        deckDao.deleteCard(deck)
    }

    suspend fun getAllCards(): List<Deck> {
        return deckDao.getAllCards()
    }
}
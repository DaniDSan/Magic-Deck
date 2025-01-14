package com.example.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CardViewModel(private val deckRepository: DeckRepository) : ViewModel() {
    // Mirar los livedata que estan raretes y no los pillo bien
    private val _cardList = MutableLiveData<List<CardData>>()
    val cardList: LiveData<List<CardData>> = _cardList
    private val _cardFound = MutableLiveData<Boolean>()
    val cardFound: LiveData<Boolean> = _cardFound
    val deckSize: LiveData<Int> = deckRepository.deckSize()

    val scryfallApi = retrofitInstance.create(ScryfallApi::class.java)
    //val deckDao = DeckDatabase(context).deckDao

    fun loadCard(cardName: String) {
        viewModelScope.launch {
            try {
                _cardList.value = scryfallApi.getCard(cardName).data
            } catch (e: Exception) {
                Log.e("MainActivity", "Error loading card: ${e.message}")
                _cardFound.value = false
            }
        }
    }

    fun getCardImage(cardData: CardData): String {
        return if (cardData.card_faces == null) {
            cardData.image_uris.normal
        } else
            cardData.card_faces!!.get(0).image_uris.normal
    }

    fun getCardBackImage(cardData: CardData): String? {
        return cardData.card_faces?.get(1)?.image_uris?.normal
    }

    fun hasCardBackImage(cardData: CardData): Boolean {
        return cardData.card_faces != null
    }

    fun addCard(cardPosition: Int) {
        cardList.value?.get(cardPosition)?.cardQuantity =
            cardList.value?.get(cardPosition)?.cardQuantity!! + 1
        _cardList.value = cardList.value
        if (cardList.value?.get(cardPosition)?.cardQuantity == 1) {
            viewModelScope.launch {
                deckRepository.insertCard(cardList.value!!.get(cardPosition))
            }
        } else {
            viewModelScope.launch {
                deckRepository.updateCard(cardList.value!!.get(cardPosition))
            }
        }
    }

    fun removeCard(cardPosition: Int) {
        if (cardList.value?.get(cardPosition)?.cardQuantity!! > 0) {
            cardList.value?.get(cardPosition)?.cardQuantity =
                cardList.value?.get(cardPosition)?.cardQuantity!! - 1
            _cardList.value = cardList.value
        }
        if (cardList.value?.get(cardPosition)?.cardQuantity == 0) {
            viewModelScope.launch {
                deckRepository.deleteCard(cardList.value!!.get(cardPosition))
            }
        } else {
            viewModelScope.launch {
                deckRepository.updateCard(cardList.value!!.get(cardPosition))
            }
        }
    }

    fun getCardQuantity(cardPosition: Int): Int {
        return cardList.value?.get(cardPosition)?.cardQuantity ?: 0
    }
}
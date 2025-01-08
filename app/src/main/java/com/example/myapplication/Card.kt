package com.example.myapplication

data class Card(
    val data: List<CardData>
)

data class CardData(
    val name: String,
    val image_uris: ImageUris,
    // Tratar las caras con un campo de bool
    val card_faces: List<CardFaces>?,
    var cardQuantity: Int = 0
)

data class ImageUris(
    val normal: String,
)

data class CardFaces(
    val name: String,
    val image_uris: ImageUris
)
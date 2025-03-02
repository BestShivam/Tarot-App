package com.example.tarotapp.data

object Repository {
    fun getCards() : List<CardData>{
        return listOf<CardData>(
            CardData(name ="The Fool"),
            CardData(name ="The Magician"),
            CardData(name ="The High Priestess"),
            CardData(name ="The Empress"),
            CardData(name ="The Emperor"),
            CardData(name ="The Hierophant"),
            CardData(name ="The Lovers"),
            CardData(name ="The Chariot"),
            CardData(name ="Strength"),
            CardData(name =" The Hermit"),
            CardData(name ="Wheel of Fortune"),
            CardData(name ="Justice"),
            CardData(name ="The Hanged Man"),
            CardData(name ="Death"),
            CardData(name ="Temperance"),
            CardData(name ="The Devil"),
            CardData(name ="The Tower"),
            CardData(name ="The Star"),
            CardData(name ="The Moon"),
            CardData(name ="The Sun"),
            CardData(name ="Judgment"),
            CardData(name ="The World")
        )
    }
}
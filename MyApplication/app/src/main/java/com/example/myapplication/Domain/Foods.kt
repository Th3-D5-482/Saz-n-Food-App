package com.example.myapplication.Domain

import java.io.Serializable

class Foods: Serializable {
    var CategoryId = 0
    var Description: String? = null
    var BestFood = false
    var Id = 0
    var LocationId = 0
    var Price = 0.0
    var ImagePath: String? = null
    var PriceId = 0
    var Star = 0.0
    var TimeId = 0
    var TimeValue = 0
    var Title: String? = null
    var numberInCart = 0
    val numberInFavorite = 0
    override fun toString(): String {
        return Title!!
    }
}
package com.example.myapplication.Domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val Id: Int = 0,
    val Name: String = "",
    val ImagePath: String = "",
    val Number: Int = 0
) : Parcelable

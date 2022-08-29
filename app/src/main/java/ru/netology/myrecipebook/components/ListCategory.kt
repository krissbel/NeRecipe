package ru.netology.myrecipebook.components

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
enum class ListCategory: Parcelable {
    European,
    Asian,
    PanAsian,
    Eastern,
    American,
    Russian,
    Mediterranean
}
package ru.netology.myrecipebook.components

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize

data class Step(
    val id: Long,
    val recipeId: Long?,
    val stepText: String
): Parcelable



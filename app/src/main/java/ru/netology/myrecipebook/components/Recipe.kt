package ru.netology.myrecipebook.components

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.text.FieldPosition
@Serializable
@Parcelize

data class Recipe(

    val id: Long,
    val author: String,
    val category: String,
    val nameRecipe: String,
    val isFavorite: Boolean = false,
    val indexPosition: Long = 0L
) : Parcelable{

}

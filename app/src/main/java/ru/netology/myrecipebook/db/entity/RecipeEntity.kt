package ru.netology.myrecipebook.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.text.FieldPosition

@Entity(tableName = "recipes")
class RecipeEntity(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "recipe_id")
    val id: Long,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "recipeName")
    val recipeName: String,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean,

)


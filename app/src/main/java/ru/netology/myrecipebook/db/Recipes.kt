package ru.netology.myrecipebook.db

import ru.netology.myrecipebook.components.Recipe
import ru.netology.myrecipebook.db.entity.RecipeEntity


internal fun RecipeEntity.toModel() = Recipe(
    id = id,
    author = author,
    category = category,
    nameRecipe = recipeName,
    isFavorite = isFavorite,
//    indexPosition = indexPosition
)

internal fun Recipe.toEntity() = RecipeEntity(
    id = id,
    author = author,
    category = category,
    recipeName = nameRecipe,
    isFavorite = isFavorite,
//    indexPosition = indexPosition
)
package ru.netology.myrecipebook.adapter

import ru.netology.myrecipebook.components.Recipe

interface RecipeInteractionListener {

    fun onRemoveClicked(recipe: Recipe)
    fun onEditClicked(recipe: Recipe)
    fun openRecipe(recipe: Recipe)
    fun onFavoriteClicked(recipe: Recipe)
}
package ru.netology.myrecipebook.adapter

import ru.netology.myrecipebook.components.Recipe
import ru.netology.myrecipebook.components.Step

interface RecipeInteractionListener {

    fun onRemoveClicked(recipe: Recipe)
    fun onEditClicked(recipe: Recipe)
    fun onAddStepClicked(recipe: Recipe?)
    fun openRecipe(recipe: Recipe)
    fun onFavoriteClicked(recipe: Recipe)


    fun onRemoveStepClicked(step: Step)
    fun onEditStepClicked(step: Step)
}
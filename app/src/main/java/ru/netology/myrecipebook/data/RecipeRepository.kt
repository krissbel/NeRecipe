package ru.netology.myrecipebook.data

import androidx.lifecycle.LiveData
import org.w3c.dom.Text
import ru.netology.myrecipebook.components.ListCategory
import ru.netology.myrecipebook.components.Recipe
import ru.netology.myrecipebook.components.Step
import ru.netology.myrecipebook.db.entity.StepEntity

interface RecipeRepository {

    val data: LiveData<List<Recipe>>
    fun getAll()
    fun delete(recipeId: Long)
    fun save(recipe: Recipe)
    fun favorite(recipeId: Long)
    fun search(searchText: String)
    fun update()
    fun getCategories(categories: List<ListCategory>)
    fun showFavorite()
    fun getRecipeById(recipeId: Long)

    val stepData: LiveData<List<Step>>
    fun saveStep(step: Step)
    fun getStepById(stepId: Long): Step
    fun deleteStep(stepId: Long)
    fun updateStep(stepId: Long, stepText:String, recipeId: Long)
    fun showStepByRecipeId(recipeId: Long?)

    companion object {
        const val NEW_RECIPE_ID = 0L
        const val NEW_STEP_ID = 0L
    }
}
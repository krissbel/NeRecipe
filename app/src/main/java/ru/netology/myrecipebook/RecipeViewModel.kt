package ru.netology.myrecipebook

import android.app.Application
import android.content.Intent
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.myrecipebook.activity.NewRecipeFragment
import ru.netology.myrecipebook.adapter.FilterInteractionListener
import ru.netology.myrecipebook.adapter.RecipeInteractionListener
import ru.netology.myrecipebook.components.ListCategory
import ru.netology.myrecipebook.components.Recipe
import ru.netology.myrecipebook.components.Step
import ru.netology.myrecipebook.data.RecipeRepository
import ru.netology.myrecipebook.data.RecipeRepository.Companion.NEW_RECIPE_ID
import ru.netology.myrecipebook.data.RecipeRepository.Companion.NEW_STEP_ID
import ru.netology.myrecipebook.data.RecipeRepositoryImpl
import ru.netology.myrecipebook.db.AppDb
import ru.netology.myrecipebook.utils.SingleLiveEvent

class RecipeViewModel(application: Application) : AndroidViewModel(application),
    RecipeInteractionListener, FilterInteractionListener {
    private val repository: RecipeRepository = RecipeRepositoryImpl(
        recipeDao = AppDb.getInstance(
            context = application
        ).recipeDao,
        stepDao = AppDb.getInstance(
            context = application
        ).stepDao
    )
    val data get() = repository.data
    val stepData get() = repository.stepData


    val navigateToRecipeScreenEvent = SingleLiveEvent<Recipe>()

    val navigateToRecipeDetails = SingleLiveEvent<Long>()
    val navigateFilterEvent = SingleLiveEvent<Unit>()
    val navigateToStepScreenEvent = SingleLiveEvent<Step>()

    /**/
    val editedRecipe = SingleLiveEvent<Recipe>()
    val currentRecipe = MutableLiveData<Recipe?>()
    val currentStep = MutableLiveData<Step?>()
    val filter = MutableLiveData<MutableSet<String>?>(mutableSetOf())

    var filterIsActive = false

    private var categoriesFilter: List<ListCategory> = ListCategory.values().toList()


    fun getEditedRecipe(): Recipe? {
        return editedRecipe.value
    }

    fun getRecipeById(recipeId: Long) = repository.getRecipeById(recipeId)


    fun showAllRecipes() {
        repository.getAll()
    }

    fun showStepByRecipeId(recipeId: Long?) {
        repository.showStepByRecipeId(recipeId)
    }


    fun showRecipesByCategories(categories: List<ListCategory>) {
        categoriesFilter = categories
        repository.getCategories(categoriesFilter)
    }


    fun onAddRecipeClicked() {
        navigateToRecipeScreenEvent.call()
    }

    fun onAddStepClicked() {
        navigateToStepScreenEvent.call()
    }

    fun onIsFavoriteClicked(isFavorite: Boolean) {
        if (!isFavorite) return

    }

    fun showFavorite() {
        repository.showFavorite()
    }

    fun onSaveClicked(authorName: String, recipeName: String, category: String) {

        if (recipeName.isBlank() || authorName.isBlank()) return

        val recipe = currentRecipe.value?.copy(
            nameRecipe = recipeName,
            author = authorName,
            category = category
        ) ?: Recipe(
            id = NEW_RECIPE_ID,
            author = authorName,
            nameRecipe = recipeName,
            category = category
        )

        repository.save(recipe)
        currentRecipe.value = null

    }

    fun onSaveStepClicked(stepText: String) {
        if (stepText.isBlank()) return
        val recipeId = currentRecipe.value?.id

        val step = currentStep.value?.copy(
            stepText = stepText
        ) ?: Step(
            id = NEW_STEP_ID,
            recipeId = recipeId,
            stepText = stepText
        )
        repository.saveStep(step)
        currentStep.value = null
        currentRecipe.value = null
    }

    fun onSearchClicked(searchText: String) {
        repository.search(searchText)
    }

    fun selectImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    override fun onRemoveClicked(recipe: Recipe) = repository.delete(recipe.id)

    override fun onEditClicked(recipe: Recipe) {
        currentRecipe.value = recipe
        navigateToRecipeScreenEvent.value = recipe
    }

    override fun openRecipe(recipe: Recipe) {
        currentRecipe.value = recipe
        navigateToRecipeDetails.value = recipe.id
    }

    override fun onFavoriteClicked(recipe: Recipe) = repository.favorite(recipe.id)

    override fun onRemoveStepClicked(step: Step) = repository.deleteStep(step.id)


    override fun onEditStepClicked(step: Step) {
        currentStep.value = step
        navigateToStepScreenEvent.value = step
    }

    override fun checkboxIsActive(category: String) {
        val listFilter = filter.value
        listFilter?.add(category)
        filter.value = listFilter
    }

    override fun checkboxNotActive(category: String) {
        val listFilter = filter.value
        listFilter?.remove(category)
        filter.value = listFilter
    }

    override fun getStatusCheckbox(category: String): Boolean {
        return filter.value?.contains(category) == true
    }

}
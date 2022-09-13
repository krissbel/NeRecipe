package ru.netology.myrecipebook.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.myrecipebook.components.ListCategory
import ru.netology.myrecipebook.components.Recipe
import ru.netology.myrecipebook.components.Step
import ru.netology.myrecipebook.db.RecipeDao
import ru.netology.myrecipebook.db.StepDao
import ru.netology.myrecipebook.db.toEntity
import ru.netology.myrecipebook.db.toModel
import toEntity
import toModel

class RecipeRepositoryImpl(private val recipeDao: RecipeDao, private val stepDao: StepDao) : RecipeRepository {

    override var stepData = stepDao.getAll().map{ entities ->
        entities.map { it.toModel() }
    }

    override var data = recipeDao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun getAll() {
        data = recipeDao.getAll().map { entities ->
            entities.map { it.toModel() }
        }
    }

      override fun getCategories(categories: List<ListCategory>){
          data = recipeDao.getCategory(categories).map{ entities->
              entities.map { it.toModel() }

          }
      }

    override fun showFavorite() {
        data = recipeDao.showFavorite().map { entities ->
            entities.map { it.toModel() }
        }
    }

    override fun getRecipeById(recipeId: Long) {
        recipeDao.getRecipeById(recipeId)
    }


    override fun saveStep(step: Step) {
       stepDao.saveStep(step.toEntity())
    }

    override fun getStepById(stepId: Long): Step {
        TODO("Not yet implemented")
    }

    override fun deleteStep(stepId: Long) = stepDao.removeByStepId(stepId)


    override fun updateStep(stepId: Long, stepText: String, recipeId: Long) =
        stepDao.updateStepById(stepText, stepId, recipeId)

    override fun showStepByRecipeId(recipeId: Long?) {
        stepData = stepDao.getStepByRecipeId(recipeId).map { entities ->
            entities.map{it.toModel()}
        }
    }


    override fun delete(recipeId: Long) = recipeDao.removeById(recipeId)

    override fun save(recipe: Recipe) {
        recipeDao.save(recipe.toEntity())
    }


    override fun favorite(recipeId: Long) = recipeDao.favoriteById(recipeId)

    override fun search(searchText: String) {
        data = recipeDao.search(searchText).map { entities ->
            entities.map { it.toModel() }
        }
    }

    override fun update() {
        recipeDao.update()
    }


}



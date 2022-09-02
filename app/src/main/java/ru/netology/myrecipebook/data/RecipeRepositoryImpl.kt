package ru.netology.myrecipebook.data

import androidx.lifecycle.map
import ru.netology.myrecipebook.components.ListCategory
import ru.netology.myrecipebook.components.Recipe
import ru.netology.myrecipebook.components.Step
import ru.netology.myrecipebook.db.RecipeDao
import ru.netology.myrecipebook.db.toEntity
import ru.netology.myrecipebook.db.toModel
import toEntity

class RecipeRepositoryImpl(private val dao: RecipeDao) : RecipeRepository {

    override var data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun getAll() {
        data = dao.getAll().map { entities ->
            entities.map { it.toModel() }
        }
    }

      override fun getCategories(categories: List<ListCategory>){
          data = dao.getCategory(categories).map{entities->
              entities.map { it.toModel() }

          }
      }

    override fun showFavorite() {
        data = dao.showFavorite().map { entities ->
            entities.map { it.toModel() }
        }
    }

//    override fun getRecipeById(recipeId: Long): Recipe {
//        data = dao.getRecipeById()
//    }


    override fun delete(recipeId: Long) = dao.removeById(recipeId)

    override fun save(recipe: Recipe) {
        dao.save(recipe.toEntity())
    }

//    override fun saveStep(step: Step) {
//        dao.saveStep(step.toEntity())
//    }

    override fun favorite(recipeId: Long) = dao.favoriteById(recipeId)

    override fun search(searchText: String) {
        data = dao.search(searchText).map { entities ->
            entities.map { it.toModel() }
        }
    }

    override fun update() {
        dao.update()
    }


}



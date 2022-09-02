package ru.netology.myrecipebook.db

import androidx.lifecycle.LiveData
import androidx.room.*
import org.w3c.dom.Text
import ru.netology.myrecipebook.components.ListCategory
import ru.netology.myrecipebook.db.entity.RecipeEntity
import ru.netology.myrecipebook.db.entity.StepEntity

@Dao

interface RecipeDao {

    @Query("SELECT * FROM recipes ORDER BY recipe_id DESC")
    fun getAll(): LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE recipe_id = :recipeId")
    fun getRecipeById(recipeId: Long): RecipeEntity


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeEntity)
   // fun insertStep(step: StepEntity)

    @Query("UPDATE recipes SET recipeName = :recipeName, author =:author WHERE recipe_id = :recipeId")
    fun updateById(recipeId: Long, recipeName: String, author:String)

//    @Query("UPDATE steps SET step_text = :stepText WHERE step_id = :stepId" )
//    fun updateStepById(stepId: Long, stepText: String)

    fun save(recipe: RecipeEntity) =
        if (recipe.id == 0L) insert(recipe) else updateById(recipe.id, recipe.recipeName, recipe.author)

//    fun saveStep(step: StepEntity) =
//        if(step.id == 0L) insertStep(step) else updateStepById(step.id, step.stepText)

    @Query("DELETE FROM recipes WHERE recipe_id = :recipeId")
    fun removeById(recipeId: Long)


    @Query(
        """
            UPDATE recipes SET
            isFavorite = CASE WHEN isFavorite THEN 0 ELSE 1 END
            WHERE recipe_id = :recipeId
            """
    )
    fun favoriteById(recipeId: Long)

    @Query("SELECT * FROM recipes WHERE isFavorite = 1")
    fun showFavorite():LiveData<List<RecipeEntity>>


    @Query("SELECT * FROM recipes WHERE recipeName LIKE '%' || :searchText || '%'")
    fun search(searchText: String): LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE category = :category")
    fun getCategory(category: List<ListCategory>):LiveData<List<RecipeEntity>>

    @Query("UPDATE recipes SET recipeName = recipeName")
    fun update()



}
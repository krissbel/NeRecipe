package ru.netology.myrecipebook.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.netology.myrecipebook.components.ListCategory
import ru.netology.myrecipebook.db.entity.RecipeEntity

@Dao

interface RecipeDao {

    @Query("SELECT * FROM recipes ORDER BY recipe_id DESC")
    fun getAll(): LiveData<List<RecipeEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeEntity)

    @Query("UPDATE recipes SET recipeName = :recipeName, author =:author WHERE recipe_id = :recipeId")
    fun updateById(recipeId: Long, recipeName: String, author:String)

    fun save(recipe: RecipeEntity) =
        if (recipe.id == 0L) insert(recipe) else updateById(recipe.id, recipe.recipeName, recipe.author)


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
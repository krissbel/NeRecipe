package ru.netology.myrecipebook.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.netology.myrecipebook.db.entity.RecipeEntity
import ru.netology.myrecipebook.db.entity.StepEntity

@Database(
    entities = [RecipeEntity::class, StepEntity::class],
    version = 1
)

abstract class AppDb : RoomDatabase() {
    abstract val recipeDao: RecipeDao
    abstract val stepDao: StepDao

    companion object {
        @Volatile
        private var instance: AppDb? = null


        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context).also { instance = it }
            }
        }

        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(
                context, AppDb::class.java, "app.db"
            ).allowMainThreadQueries()
                .build()
    }

}
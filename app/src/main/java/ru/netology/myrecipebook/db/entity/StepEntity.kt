package ru.netology.myrecipebook.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "steps",
foreignKeys = [ForeignKey(
    entity = RecipeEntity::class,
    parentColumns = ["recipe_id"],
    childColumns = ["id_recipe"],
    onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.NO_ACTION
)])


class StepEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "step_id")
    val id:Long,

    @ColumnInfo(name = "id_recipe")
    val recipeId: Long?,

    @ColumnInfo(name = "step_text")
    val stepText:String
)

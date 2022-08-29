package ru.netology.myrecipebook.db

import ru.netology.myrecipebook.components.Category
import ru.netology.myrecipebook.db.entity.CategoryEntity


internal fun CategoryEntity.toModel() = Category(
    id = id,
    categoryName = categoryName,
    isVisible = isVisible

)

internal fun Category.toEntity() = CategoryEntity(
    id = id,
    categoryName = categoryName,
    isVisible = isVisible
)
package com.mobiledev98.foodapp.model

import java.io.Serializable

data class Categories(
    val categories: List<Category>
)

data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
): Serializable
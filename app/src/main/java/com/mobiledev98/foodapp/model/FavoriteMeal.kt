package com.mobiledev98.foodapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_meal")
data class FavoriteMeal (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nameMeal: String? = null,
    val src: String,
)
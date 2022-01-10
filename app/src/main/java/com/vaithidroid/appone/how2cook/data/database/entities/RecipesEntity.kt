package com.vaithidroid.appone.how2cook.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vaithidroid.appone.how2cook.models.FoodRecipe
import com.vaithidroid.appone.how2cook.util.Constants.Companion.DATABASE_NAME
import com.vaithidroid.appone.how2cook.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id : Int = 0
}
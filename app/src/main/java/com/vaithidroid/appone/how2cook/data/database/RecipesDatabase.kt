package com.vaithidroid.appone.how2cook.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vaithidroid.appone.how2cook.data.database.entities.FavoriteEntity
import com.vaithidroid.appone.how2cook.data.database.entities.RecipesEntity

@Database(
    entities = [RecipesEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipeTypeConverter::class)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun recipeDao() : RecipesDao

}
package com.vaithidroid.appone.how2cook.data.database

import androidx.room.*
import com.vaithidroid.appone.how2cook.data.database.entities.FavoriteEntity
import com.vaithidroid.appone.how2cook.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes() : Flow<List<RecipesEntity>>

    @Query("SELECT * FROM favourite_recipe_table ORDER BY id ASC")
    fun readFavouriteRecipe() : Flow<List<FavoriteEntity>>

    @Delete
    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favourite_recipe_table")
    suspend fun deleteAllFavoriteRecipe()

}
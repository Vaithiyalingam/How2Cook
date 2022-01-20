package com.vaithidroid.appone.how2cook.data

import com.vaithidroid.appone.how2cook.data.database.RecipesDao
import com.vaithidroid.appone.how2cook.data.database.entities.FavoriteEntity
import com.vaithidroid.appone.how2cook.data.database.entities.FoodJokeEntity
import com.vaithidroid.appone.how2cook.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readRecipe() : Flow<List<RecipesEntity>>{
        return recipesDao.readRecipes()
    }

    fun readFavoriteRecipe() : Flow<List<FavoriteEntity>>{
        return recipesDao.readFavouriteRecipe()
    }

    fun readFoodJoke(): Flow<List<FoodJokeEntity>> {
        return recipesDao.readFoodJoke()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }

    suspend fun insertFavoriteRecipe(favoriteEntity: FavoriteEntity){
        recipesDao.insertFavoriteRecipe(favoriteEntity)
    }

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        recipesDao.insertFoodJoke(foodJokeEntity)
    }

    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity){
        recipesDao.deleteFavoriteRecipe(favoriteEntity)
    }

    suspend fun deleteAllFavoriteRecipe(){
        recipesDao.deleteAllFavoriteRecipe()
    }

}
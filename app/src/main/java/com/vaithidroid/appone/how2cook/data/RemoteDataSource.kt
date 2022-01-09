package com.vaithidroid.appone.how2cook.data

import com.vaithidroid.appone.how2cook.data.network.FoodRecipeApi
import com.vaithidroid.appone.how2cook.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipeApi: FoodRecipeApi
) {

    suspend fun getRecipes(queries : Map<String, String>) : Response<FoodRecipe>{
        return foodRecipeApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQueries : Map<String,String>) : Response<FoodRecipe>{
        return foodRecipeApi.searchRecipes(searchQueries)
    }

}
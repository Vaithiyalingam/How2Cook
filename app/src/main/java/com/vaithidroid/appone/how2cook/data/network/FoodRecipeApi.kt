package com.vaithidroid.appone.how2cook.data.network

import com.vaithidroid.appone.how2cook.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipeApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries : Map<String, String>
    ) : Response<FoodRecipe>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQueries : Map<String, String>
    ) : Response<FoodRecipe>

}
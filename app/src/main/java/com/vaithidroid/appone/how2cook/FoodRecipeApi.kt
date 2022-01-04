package com.vaithidroid.appone.how2cook

import com.vaithidroid.appone.how2cook.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipeApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries : Map<String, String>
    ) : Response<FoodRecipe>

}
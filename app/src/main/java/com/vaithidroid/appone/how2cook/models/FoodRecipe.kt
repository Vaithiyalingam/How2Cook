package com.vaithidroid.appone.how2cook.models

import com.google.gson.annotations.SerializedName


data class FoodRecipe(
    @SerializedName("results")
    val results: List<Result>
)
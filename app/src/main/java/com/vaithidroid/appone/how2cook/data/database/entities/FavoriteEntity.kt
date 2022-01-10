package com.vaithidroid.appone.how2cook.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vaithidroid.appone.how2cook.models.Result
import com.vaithidroid.appone.how2cook.util.Constants.Companion.FAVOURITE_RECIPE_TABLE

@Entity(tableName = FAVOURITE_RECIPE_TABLE)
class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
) {
}
package com.vaithidroid.appone.how2cook.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.vaithidroid.appone.how2cook.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.vaithidroid.appone.how2cook.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.vaithidroid.appone.how2cook.util.Constants.Companion.PREFERENCE_BACK_ONLINE
import com.vaithidroid.appone.how2cook.util.Constants.Companion.PREFERENCE_DIET_TYPE
import com.vaithidroid.appone.how2cook.util.Constants.Companion.PREFERENCE_DIET_TYPE_ID
import com.vaithidroid.appone.how2cook.util.Constants.Companion.PREFERENCE_MEAL_TYPE
import com.vaithidroid.appone.how2cook.util.Constants.Companion.PREFERENCE_MEAL_TYPE_ID
import com.vaithidroid.appone.how2cook.util.Constants.Companion.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCE_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKey{
        val selectedMealType = stringPreferencesKey(PREFERENCE_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCE_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCE_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCE_DIET_TYPE_ID)
        val backOnline = booleanPreferencesKey(PREFERENCE_BACK_ONLINE)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int){
        context.dataStore.edit {preference ->
            preference[PreferenceKey.selectedMealType] = mealType
            preference[PreferenceKey.selectedMealTypeId] = mealTypeId
            preference[PreferenceKey.selectedDietType] = dietType
            preference[PreferenceKey.selectedDietTypeId] = dietTypeId
        }
    }

    suspend fun saveBackOnline(backOnline: Boolean){
        context.dataStore.edit { preference->
            preference[PreferenceKey.backOnline] = backOnline
        }
    }

    val readMealAndDietType : Flow<MealAndDietType> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map { preference->
            val selectedMealType = preference[PreferenceKey.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preference[PreferenceKey.selectedMealTypeId] ?: 0
            val selectedDietType = preference[PreferenceKey.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preference[PreferenceKey.selectedDietTypeId] ?: 0

            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

    val readBackOnline : Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map { preference->
            val backOnline = preference[PreferenceKey.backOnline] ?: false
            backOnline
        }

    data class MealAndDietType(
        val selectedMealType : String,
        val selectedMealTypeID  : Int,
        val selectedDietType : String,
        val selectedDietTypeId : Int
    )

}
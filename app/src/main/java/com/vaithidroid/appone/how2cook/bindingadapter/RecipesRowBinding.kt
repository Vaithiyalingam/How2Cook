package com.vaithidroid.appone.how2cook.bindingadapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.vaithidroid.appone.how2cook.R
import com.vaithidroid.appone.how2cook.models.Result
import com.vaithidroid.appone.how2cook.ui.fragments.recipe.RecipeFragmentDirections
import org.jsoup.Jsoup
import java.lang.Exception

class RecipesRowBinding {


    companion object{

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout: ConstraintLayout, result: Result?){
            recipeRowLayout.setOnClickListener {
                try {
                    val action =
                        RecipeFragmentDirections.actionRecipeFragmentToDetialsActivity(result!!)
                    recipeRowLayout.findNavController().navigate(action)
                }catch (e: Exception){
                    Log.d("RecipeClickListener", "onRecipeClickLiastener: ${e.message}")
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl : String){
            imageView.load(imageUrl){
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view : View, vegan : Boolean){
            if (vegan){
                when(view){
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }

        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView, description: String?){
            if (description != null){
                val desc = Jsoup.parse(description).text()
                textView.text = desc
            }
        }

    }

}
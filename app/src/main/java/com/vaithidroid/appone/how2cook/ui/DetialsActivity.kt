package com.vaithidroid.appone.how2cook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.vaithidroid.appone.how2cook.R
import com.vaithidroid.appone.how2cook.adapter.PagerAdapter
import com.vaithidroid.appone.how2cook.data.database.entities.FavoriteEntity
import com.vaithidroid.appone.how2cook.ui.fragments.IngredientsFragment
import com.vaithidroid.appone.how2cook.ui.fragments.InstructionFragment
import com.vaithidroid.appone.how2cook.ui.fragments.OverviewFragment
import com.vaithidroid.appone.how2cook.util.Constants.Companion.RECIPE_RESULT_KEY
import com.vaithidroid.appone.how2cook.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detials.*
import java.lang.Exception

@AndroidEntryPoint
class DetialsActivity : AppCompatActivity() {


    private val args by navArgs<DetialsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    private var recipeSaved = false
    private var savedRecipeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detials)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingrediants")
        titles.add("Instruction")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        val adapter = PagerAdapter(
            resultBundle,
            fragments,
            titles,
            supportFragmentManager
        )

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favorites_menu)
        checkSavedRecipes(menuItem!!)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        } else if(item.itemId == R.id.save_to_favorites_menu && !recipeSaved){
            saveToFavorites(item)
        } else if (item.itemId == R.id.save_to_favorites_menu && recipeSaved) {
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipe.observe(this, { favoritesEntity ->
            try {
                for (savedRecipe in favoritesEntity) {
                    if (savedRecipe.result.recipeId == args.result.recipeId) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                    } else {
                        changeMenuItemColor(menuItem, R.color.white)
                    }
                }
            } catch (e: Exception) {
                Log.d("DetailsActivity", e.message.toString())
            }
        })
    }

    private fun saveToFavorites(item: MenuItem) {

        val favoriteEntity = FavoriteEntity(
            id = 0,
            result = args.result
        )
        mainViewModel.insertFavoriteRecipe(favoriteEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipe Saved.")
        recipeSaved = true

    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity =
            FavoriteEntity(
                savedRecipeId,
                args.result
            )
        mainViewModel.deleteFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar("Removed from Favorites.")
        recipeSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this,color ))
    }
}
package com.vaithidroid.appone.how2cook.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.vaithidroid.appone.how2cook.R
import com.vaithidroid.appone.how2cook.adapter.IngredientsAdapter
import com.vaithidroid.appone.how2cook.models.ExtendedIngredient
import com.vaithidroid.appone.how2cook.models.Result
import com.vaithidroid.appone.how2cook.util.Constants
import kotlinx.android.parcel.RawValue
import kotlinx.android.synthetic.main.fragment_ingredients.view.*

class IngredientsFragment : Fragment() {

    private val ingredientsAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_ingredients, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        setUpRecyclerView(view)

        myBundle?.extendedIngredients?.let {
            ingredientsAdapter.setData(it as @RawValue List<ExtendedIngredient>)
        }

        return view
    }

    private fun setUpRecyclerView(view: View){
        view.ingredients_recyclerview.adapter = ingredientsAdapter
        view.ingredients_recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

}
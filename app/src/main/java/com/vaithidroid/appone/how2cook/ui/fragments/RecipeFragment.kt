package com.vaithidroid.appone.how2cook.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vaithidroid.appone.how2cook.R
import kotlinx.android.synthetic.main.fragment_recipe.view.*

class RecipeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipe, container, false)
        view.recyclerview.showShimmer()
        return view
    }
}
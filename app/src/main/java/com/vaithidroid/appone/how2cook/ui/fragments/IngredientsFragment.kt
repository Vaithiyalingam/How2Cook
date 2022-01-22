package com.vaithidroid.appone.how2cook.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.vaithidroid.appone.how2cook.R
import com.vaithidroid.appone.how2cook.adapter.IngredientsAdapter
import com.vaithidroid.appone.how2cook.databinding.FragmentIngredientsBinding
import com.vaithidroid.appone.how2cook.models.ExtendedIngredient
import com.vaithidroid.appone.how2cook.models.Result
import com.vaithidroid.appone.how2cook.util.Constants

class IngredientsFragment : Fragment() {

    private val ingredientsAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }
    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentIngredientsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        setUpRecyclerView()

        myBundle?.extendedIngredients?.let {
            ingredientsAdapter.setData(it as  List<ExtendedIngredient>)
        }

        return binding.root
    }

    private fun setUpRecyclerView(){
        binding.ingredientsRecyclerview.adapter = ingredientsAdapter
        binding.ingredientsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
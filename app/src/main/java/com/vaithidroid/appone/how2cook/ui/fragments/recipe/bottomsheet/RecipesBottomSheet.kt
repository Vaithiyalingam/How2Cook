package com.vaithidroid.appone.how2cook.ui.fragments.recipe.bottomsheet

import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.vaithidroid.appone.how2cook.R
import com.vaithidroid.appone.how2cook.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.vaithidroid.appone.how2cook.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.vaithidroid.appone.how2cook.viewmodels.RecipeViewModel
import kotlinx.android.synthetic.main.recipes_bottom_sheet.view.*
import java.util.*

class RecipesBottomSheet : BottomSheetDialogFragment() {

    private lateinit var recipeViewModel: RecipeViewModel

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeViewModel= ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.recipes_bottom_sheet, container, false)

        recipeViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner,{value->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeID, mView.mealType_chipGroup)
            updateChip(value.selectedDietTypeId, mView.dietType_chipGroup)
        })

        mView.mealType_chipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedChip = chip.text.toString().lowercase(Locale.ROOT)
            mealTypeChip = selectedChip
            mealTypeChipId = selectedChipId
        }

        mView.dietType_chipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedChip = chip.text.toString().lowercase(Locale.ROOT)
            dietTypeChip = selectedChip
            dietTypeChipId = selectedChipId
        }

        mView.apply_btn.setOnClickListener {
            recipeViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )
            val action =
                RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipeFragment2(true)
            findNavController().navigate(action)
        }

        return mView
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0){
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            }catch (e: Exception){
                Log.d("ecipebottomsheet", "updateChip: ${e.message.toString()} ")
            }
        }
    }


}
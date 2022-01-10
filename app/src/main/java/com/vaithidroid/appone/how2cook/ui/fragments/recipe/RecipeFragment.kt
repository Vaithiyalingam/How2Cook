package com.vaithidroid.appone.how2cook.ui.fragments.recipe

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.vaithidroid.appone.how2cook.R
import com.vaithidroid.appone.how2cook.viewmodels.MainViewModel
import com.vaithidroid.appone.how2cook.adapter.RecipesAdapter
import com.vaithidroid.appone.how2cook.databinding.FragmentRecipeBinding
import com.vaithidroid.appone.how2cook.util.NetworkListener
import com.vaithidroid.appone.how2cook.util.NetworkResult
import com.vaithidroid.appone.how2cook.util.observeOnce
import com.vaithidroid.appone.how2cook.viewmodels.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeFragment : Fragment(), SearchView.OnQueryTextListener {

    private val args by navArgs<RecipeFragmentArgs>()
    private var _binding : FragmentRecipeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipeViewModel: RecipeViewModel
    private val mAdapter by lazy { RecipesAdapter() }
    private lateinit var networkListener: NetworkListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipeViewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel

        setHasOptionsMenu(true)
        setUpRecyclerView()

        recipeViewModel.readBackOnline.observe(viewLifecycleOwner,{
            recipeViewModel.backOnline = it
        })

        binding.recipesFab.setOnClickListener {
            if (recipeViewModel.networkStatus) {
                findNavController().navigate(R.id.action_recipeFragment_to_recipesBottomSheet)
            }else{
                recipeViewModel.showNetworkStatus()
            }
        }

        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status->
                    Log.d("NetworkListener", status.toString())
                    recipeViewModel.networkStatus = status
                    recipeViewModel.showNetworkStatus()
                    readDatabase()
                }
        }

        return binding.root
    }

    private fun setUpRecyclerView(){
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmer()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled= true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipe.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    Log.d("readdatabase", "readDatabase: Local data called",)
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmer()
                } else {
                    requestApiData()
                }
            })
        }
    }

    private fun requestApiData(){
        Log.d("requestapidata", "requestApiData: Remote data called", )
        mainViewModel.getRecipes(recipeViewModel.applyQueries())
        mainViewModel.recipeResponse.observe(viewLifecycleOwner, { response ->
            when(response){
                is NetworkResult.Success -> {
                    hideShimmer()
                    response.data?.let {
                        mAdapter.setData(it)
                    }
                }
                is NetworkResult.Error -> {
                    hideShimmer()
                    loadDataFromcache()
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmer()
                }
            }
        })
    }

    private fun searchApiData(searchQuery: String){
        showShimmer()
        mainViewModel.searchRecipes(recipeViewModel.applySearchQueries(searchQuery))
        mainViewModel.searchResponse.observe(viewLifecycleOwner,{response->
            when(response){
                is NetworkResult.Success -> {
                    hideShimmer()
                    val foodRecipe = response.data
                    foodRecipe?.let {
                        mAdapter.setData(it)
                    }
                }
                is NetworkResult.Error -> {
                    hideShimmer()
                    loadDataFromcache()
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmer()
                }
            }
        })
    }

    private fun loadDataFromcache(){
        lifecycleScope.launch {
            mainViewModel.readRecipe.observe(viewLifecycleOwner,{database ->
                if (database.isNotEmpty()){
                    mAdapter.setData(database[0].foodRecipe)
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
//        setUpRecyclerView()
    }

    private fun showShimmer(){
        binding.recyclerview.showShimmer()
    }

    private fun hideShimmer(){
        binding.recyclerview.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
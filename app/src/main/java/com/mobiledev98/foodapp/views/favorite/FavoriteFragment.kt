package com.mobiledev98.foodapp.views.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mobiledev98.foodapp.adapter.FavoriteAdapter
import com.mobiledev98.foodapp.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favMealsAdapter:FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        prepareRecyclerView()
        showData()
        return binding.root
    }

    private fun prepareRecyclerView() {
        favMealsAdapter = FavoriteAdapter()
        binding.favRecView.apply {
            adapter = favMealsAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun showData(){
        favoriteViewModel.getAllSavedMeals()
        favoriteViewModel.favoriteMeal.observe(viewLifecycleOwner){
            if(!it.isNullOrEmpty()){
                binding.tvFavEmpty.visibility = View.GONE
                favMealsAdapter.setData(it)
            }
        }
    }
}
package com.mobiledev98.foodapp.views.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.flexbox.*
import com.mobiledev98.foodapp.R
import com.mobiledev98.foodapp.adapter.CategoriesAdapter
import com.mobiledev98.foodapp.adapter.MostPopularAdapter
import com.mobiledev98.foodapp.adapter.OnItemCategoryClicked
import com.mobiledev98.foodapp.adapter.SuggestAdapter
import com.mobiledev98.foodapp.databinding.ActivityBottomTabsBinding
import com.mobiledev98.foodapp.databinding.FragmentHomeBinding
import com.mobiledev98.foodapp.helper.Constant
import com.mobiledev98.foodapp.model.Category
import com.mobiledev98.foodapp.views.MealActivity

class HomeFragment : Fragment() {

    private var homeViewModel: HomeViewModel? = null
    private var categoriesAdapter: CategoriesAdapter? = null
    private var mostPopularAdapter: MostPopularAdapter? = null
    private var suggestAdapter: SuggestAdapter? = null
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        updateCategoriesData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        showCategories()
        showPopularMeal()
        showSuggestedMeal()
    }


    private fun initAdapter() {
        categoriesAdapter = CategoriesAdapter()
        mostPopularAdapter = MostPopularAdapter()
        suggestAdapter = SuggestAdapter()
    }


    private fun updateCategoriesData() {
        homeViewModel?.getDataToShow()
    }

    private fun showCategories() {
        val layoutManager = FlexboxLayoutManager(context).apply {
            justifyContent = JustifyContent.CENTER
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
        categoriesAdapter?.let { adapter ->
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = adapter
            homeViewModel?.categoriesData?.observe(viewLifecycleOwner) {
                adapter.setData(it)
                adapter.setOnclickCategory(object : OnItemCategoryClicked {
                    override fun onClickListener(category: Category) {
                        val intent = Intent(activity, MealActivity::class.java)
                        intent.putExtra(Constant.CATEGORY_NAME.name, category.strCategory)
                        startActivity(intent)
                    }
                })
            }
        }
    }

    private fun showPopularMeal() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mostPopularAdapter?.let { adapter ->
            binding.recViewMealsPopular.layoutManager = layoutManager
            binding.recViewMealsPopular.adapter = adapter
            homeViewModel?.mealsData?.observe(viewLifecycleOwner) {
                adapter.setData(it)
            }
        }
    }

    private fun showSuggestedMeal() {
        val uiHandler = Handler(Looper.getMainLooper())
        suggestAdapter?.let { adapter ->
            binding.viewPager.adapter = adapter
            homeViewModel?.suggestData?.observe(viewLifecycleOwner) {
                adapter.setData(it)
                binding.circleIndicator.setViewPager(binding.viewPager)
                val runnable = Runnable {
                    if (binding.viewPager.currentItem == 5) {
                        binding.viewPager.currentItem = 0
                    } else {
                        binding.viewPager.currentItem = binding.viewPager.currentItem + 1
                    }
                }
                binding.viewPager.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        uiHandler.removeCallbacks(runnable)
                        uiHandler.postDelayed(runnable, 5000)
                    }
                })
            }
        }
    }
}
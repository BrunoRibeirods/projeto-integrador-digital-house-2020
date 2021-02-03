package com.example.filmly.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmly.R
import com.example.filmly.adapters.PopularActorsAdapter
import com.example.filmly.adapters.PopularMoviesAdapter
import com.example.filmly.adapters.PopularTVAdapter
import com.example.filmly.adapters.TrendingAdapter
import com.example.filmly.data.model.CardDetail
import com.example.filmly.repository.ServicesRepository
import com.example.filmly.repository.StatesRepository
import com.example.filmly.utils.CardDetailNavigation
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var repository: ServicesRepository
    private val popularMoviesAdapter = PopularMoviesAdapter(CardDetail.FILM, CardDetailNavigation { detail ->
        val action = HomeFragmentDirections.actionHomeFragmentToCardDetailFragment(detail)
        findNavController().navigate(action)
    })

    private val popularTVAdapter = PopularTVAdapter(CardDetail.SERIE, CardDetailNavigation { detail ->
        val action = HomeFragmentDirections.actionHomeFragmentToCardDetailFragment(detail)
        findNavController().navigate(action)
    })

    private val popularActorsAdapter = PopularActorsAdapter(CardDetail.ACTOR, CardDetailNavigation { detail ->
        val action = HomeFragmentDirections.actionHomeFragmentToCardDetailFragment(detail)
        findNavController().navigate(action)
    })

    private val trendingAdapter = TrendingAdapter(CardDetail.TRENDING, CardDetailNavigation { detail ->
        val action = HomeFragmentDirections.actionHomeFragmentToCardDetailFragment(detail)
        findNavController().navigate(action)
    })

    val viewModel by viewModels<HomeViewModel>() {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        repository = ServicesRepository.getInstance(requireContext())

        //ANTIGO
//        homeAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
//            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
//                super.onItemRangeInserted(positionStart, itemCount)
//                if (!view.rv_homeLists.canScrollVertically(-1)) {
//                    view.rv_homeLists.scrollToPosition(0)
//                }
//            }
//        })


        setRecyclerViews(view)

        //Update database
        viewModel.refreshLists()

        view.tv_greetings.text =
            getString(R.string.hello_wil, StatesRepository.userInformation.value?.name)

        view.civ_profileImage.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        return view
    }

    private fun setRecyclerViews(view: View) {
        view.rv_trending.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingAdapter
        }

        view.rv_popular_movies.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularMoviesAdapter
        }

        view.rv_popular_atores.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularActorsAdapter
        }

        view.rv_popular_series.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularTVAdapter
        }

        viewModel.getTrending().observe(viewLifecycleOwner) {
            trendingAdapter.submitList(it)
        }

        lifecycleScope.launch {
            viewModel.getAllPopularSeries().collect {
                popularTVAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModel.getAllPopularActors().collect {
                popularActorsAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModel.getAllPopularMovies().collect {
                popularMoviesAdapter.submitData(it)
            }

        }
    }
}
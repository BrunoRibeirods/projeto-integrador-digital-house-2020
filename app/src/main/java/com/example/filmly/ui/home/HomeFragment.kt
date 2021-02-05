package com.example.filmly.ui.home

import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.bumptech.glide.Glide
import com.example.filmly.R
import com.example.filmly.adapters.PopularActorsAdapter
import com.example.filmly.adapters.PopularMoviesAdapter
import com.example.filmly.adapters.PopularTVAdapter
import com.example.filmly.adapters.TrendingAdapter
import com.example.filmly.data.model.CardDetail
import com.example.filmly.data.model.HeadLists
import com.example.filmly.data.model.UserInformation
import com.example.filmly.repository.ServicesRepository
import com.example.filmly.repository.StatesRepository
import com.example.filmly.utils.CardDetailNavigation
import com.example.filmly.utils.SeeMoreNavigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.cards_list_item.view.*
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

    private lateinit var repositoryStates: StatesRepository
    private lateinit var auth: FirebaseAuth


    val viewModel by viewModels<HomeViewModel>() {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel(repository, repositoryStates) as T
            }
        }
    }

    override fun onStart() {
        super.onStart()
        updateUI(auth.currentUser)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        auth = FirebaseAuth.getInstance()

        repository = ServicesRepository.getInstance(requireContext())
      
        repositoryStates = StatesRepository

        updateUI(auth.currentUser)

        setSeeMoreClicks(view)
        setRecyclerViews(view)

        //Update database
        viewModel.refreshLists()
        updateUI(auth.currentUser)

        if(auth.currentUser?.displayName == null){
            view.tv_greetings.text = getString(R.string.hello_wil, activity?.intent?.getStringExtra("name"))
        }else{
            view.tv_greetings.text = getString(R.string.hello_wil, viewModel.statesRepository.userInformation.value?.name)
        }


        Log.i("tainu", "oi")


        Glide.with(view)
            .load(auth.currentUser?.photoUrl)
            .error(R.drawable.profile_placeholder)
            .fallback(R.drawable.profile_placeholder)
            .into(view.civ_profileImage)

        view.civ_profileImage.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        return view
    }

    private fun setSeeMoreClicks(view: View) {

        view.tv_title_trending.text = viewModel.trendingMessage()
        view.tv_seeMore_trending.setOnClickListener {
            val titleMessage = viewModel.trendingMessage()
            val headList =
                HeadLists(titleMessage, trendingAdapter.currentList, CardDetail.TRENDING)
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToViewMoreFragment(headList))
        }

        view.tv_seeMore_filmes.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToViewMoreFragment(
                    type = "movie"
                )
            )
        }
        view.tv_seeMore_series.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToViewMoreFragment(
                    type = "tv"
                )
            )
        }
        view.tv_seeMore_atores.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToViewMoreFragment(
                    type = "person"
                )
            )
        }
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
            viewModel.getAllPopularMovies().collect {
                popularMoviesAdapter.submitData(it)
            }
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
    }



    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            viewModel.saveInformation(UserInformation(user.displayName.toString(), user.email.toString(), user.photoUrl.toString(), null, null))
        } else {
            Log.i("Account", "Nenhum usuario conectado.")
        }
    }
}
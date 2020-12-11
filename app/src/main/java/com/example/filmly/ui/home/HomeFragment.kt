package com.example.filmly.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmly.R
import com.example.filmly.adapters.HomeListsAdapter
import com.example.filmly.data.model.Card
import com.example.filmly.data.model.CardDetail
import com.example.filmly.data.model.HeadLists
import com.example.filmly.repository.ServicesRepository
import com.example.filmly.repository.StatesRepository
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    private lateinit var repository: ServicesRepository
    private var clickNum = 1

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

        val listavazia = emptyList<HeadLists>()

        viewModel.getTrendingLive("all", "day")
        viewModel.getTrendingLive("tv", "day")
        viewModel.getTrendingLive("movie", "day")
        viewModel.getTrendingLive("person", "day")

        view.tv_greetings.text =
            getString(R.string.hello_wil, StatesRepository.userInformation.value?.name)

        view.civ_profileImage.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        val recyclerView = view.rv_homeLists

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = HomeListsAdapter(listavazia, HomeListsAdapter.SeeMoreNavigation { trending ->
                val action = HomeFragmentDirections.actionHomeFragmentToViewMoreFragment(trending)
                findNavController().navigate(action)
            })
            setHasFixedSize(true)
        }

        viewModel.trendingLive.observe(viewLifecycleOwner) {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = HomeListsAdapter(
                    getResultsLists(
                        it.results.map {
                            when (it.media_type) {
                                "movie" -> it.convertToCard()
                                "tv" -> it.convertToTv()
                                "person" -> it.convertToPerson()
                                else -> it.convertToCard()
                            }
                        },
                        when (it.results.map { it.media_type }.distinct().joinToString()) {
                            "movie" -> CardDetail.FILM
                            "tv" -> CardDetail.SERIE
                            "person" -> CardDetail.ACTOR
                            else -> CardDetail.TRENDING
                        }

                    ), HomeListsAdapter.SeeMoreNavigation { trending ->
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToViewMoreFragment(trending)
                        findNavController().navigate(action)
                    })
                setHasFixedSize(true)
            }
        }
        return view
    }

    fun getResultsLists(listaFilmes: List<Card>, cardInfo: Int): List<HeadLists> {

        val tipo = when (cardInfo) {
            CardDetail.FILM -> "filmes"
            CardDetail.ACTOR -> "pessoas"
            CardDetail.SERIE -> "séries"
            CardDetail.TRENDING -> "trending"
            else -> "outros"
        }

        val order = StatesRepository.homeListsOrder

        val roles: HashMap<String, Int> = hashMapOf(
            "Top trending de hoje" to order.indexOf("trending"),
            "Top séries de hoje" to order.indexOf("series"),
            "Top filmes de hoje" to order.indexOf("films"),
            "Top pessoas de hoje" to order.indexOf("actors")
        )

        viewModel.headLists.add(HeadLists("Top $tipo de hoje", listaFilmes, cardInfo))
        viewModel.headLists.sortWith(Comparator { t, t2 ->
            return@Comparator roles[t.titleMessage]!! - roles[t2.titleMessage]!!
        })

        return viewModel.headLists.distinctBy { it.titleMessage }
    }


}
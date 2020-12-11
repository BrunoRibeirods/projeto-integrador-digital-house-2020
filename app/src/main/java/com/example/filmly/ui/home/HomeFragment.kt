package com.example.filmly.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmly.R
import com.example.filmly.adapters.HomeListsAdapter
import com.example.filmly.adapters.SearchListsAdapter
import com.example.filmly.data.model.Card
import com.example.filmly.data.model.HeadLists
import com.example.filmly.network.TmdbApiteste
import com.example.filmly.repository.StatesRepository
import com.example.filmly.ui.search.SearchViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    private val serviceApi = TmdbApiteste
    lateinit var listaH:MutableList<HeadLists>
    private var clickNum = 1

    val viewModel by viewModels<HomeViewModel>(){
        object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel(serviceApi.retrofitService) as T
            }
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val listavazia = emptyList<HeadLists>()
        listaH = mutableListOf()




        viewModel.getTrending("all", "day")
        viewModel.getTrending("tv", "day")
        viewModel.getTrending("movie", "day")
        viewModel.getTrending("person", "day")




        view.tv_greetings.text = getString(R.string.hello_wil, StatesRepository.userInformation.value?.name)

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

        viewModel.trendingLive.observe(viewLifecycleOwner){
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = HomeListsAdapter(getResultsLists(
                    it.results.map {
                                   when(it.media_type){
                                       "movie" -> it.convertToCard()
                                       "tv" -> it.convertToTv()
                                       "person" -> it.convertToPerson()
                                       else -> it.convertToCard()
                                   }
                    },
                    when(it.results.map { it.media_type }.distinct().joinToString()){
                        "movie" -> "filmes"
                        "tv" -> "series"
                        "person" -> "pessoas"
                        else -> "trending"
                    }

                ), HomeListsAdapter.SeeMoreNavigation { trending ->
                    val action = HomeFragmentDirections.actionHomeFragmentToViewMoreFragment(trending)
                    findNavController().navigate(action)
                })
                setHasFixedSize(true)
            }
        }




        return view
    }


    fun getResultsLists(listaFilmes: List<Card>, tipo: String): List<HeadLists> {
        Log.i("tipo", tipo)
        val roles: HashMap<String, Int> = hashMapOf(
            "Top trending de hoje" to 0,
            "Top series de hoje" to 1,
            "Top filmes de hoje" to 2,
            "Top pessoas de hoje" to 3
        )




        listaH.add(HeadLists("Top $tipo de hoje", listaFilmes))
        listaH.sortWith(Comparator { t, t2 ->
            return@Comparator roles[t.titleMessage]!! - roles[t2.titleMessage]!!
        })



        return listaH.distinctBy { it.titleMessage }
    }



}
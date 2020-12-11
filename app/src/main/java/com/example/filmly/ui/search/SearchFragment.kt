package com.example.filmly.ui.search

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmly.R
import com.example.filmly.adapters.SearchListsAdapter
import com.example.filmly.data.model.Card
import com.example.filmly.data.model.CardDetail
import com.example.filmly.data.model.HeadLists
import com.example.filmly.repository.ServicesRepository
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {
    private lateinit var repository: ServicesRepository
    private var clickNum = 1

    val viewModel by viewModels<SearchViewModel>(){
        object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SearchViewModel(repository) as T
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        
        repository = ServicesRepository.getInstance(requireContext())

        val listavazia = emptyList<HeadLists>()


        view.searchView.requestFocus()

        if (view.searchView.requestFocus() && clickNum == 1) {
                clickNum+= 2
            (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
            viewModel.updateMoviesLive("Vingadores")
            viewModel.updateTvLive("Game of")
            viewModel.updatePersonLive("T")
        }



            view.rv_searchResults.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = SearchListsAdapter(
                    listavazia,
                    SearchListsAdapter.SeeMoreNavigation { headLists ->
                        val action =
                            SearchFragmentDirections.actionSearchFragmentToViewMoreFragment(
                                headLists
                            )
                        findNavController().navigate(action)
                    })
                setHasFixedSize(true)
             }


        view.searchView.setOnKeyListener { view, i, keyEvent ->
            if(i == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_UP && !view.searchView.text.isEmpty()){
                viewModel.headLists = mutableListOf()
                viewModel.updateMoviesLive(searchView.text.toString())
                viewModel.updateTvLive(searchView.text.toString())
                viewModel.updatePersonLive(searchView.text.toString())
                val imm: InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(searchView.windowToken, 0)
                return@setOnKeyListener true
            }
            false
        }

        view.btn_search.setOnClickListener {
            if(!view.searchView.text.isEmpty())
            viewModel.headLists = mutableListOf()
            viewModel.updateMoviesLive(searchView.text.toString())
            viewModel.updateTvLive(searchView.text.toString())
            viewModel.updatePersonLive(searchView.text.toString())
            val imm: InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(searchView.windowToken, 0)

        }


        viewModel.moviesLive.observe(viewLifecycleOwner){
            if(it.results.isNotEmpty()){
            view.rv_searchResults.adapter = SearchListsAdapter(getResultsLists(
                it.results.map { it.convertToFilm() },

                CardDetail.FILM

            ), SearchListsAdapter.SeeMoreNavigation { headLists ->
                val action =
                    SearchFragmentDirections.actionSearchFragmentToViewMoreFragment(headLists)
                findNavController().navigate(action)
            })
            }else{
                view.rv_searchResults.adapter = SearchListsAdapter(
                    listavazia, SearchListsAdapter.SeeMoreNavigation { headLists ->
                    val action =
                        SearchFragmentDirections.actionSearchFragmentToViewMoreFragment(headLists)
                    findNavController().navigate(action)
                })
            }
        }

        viewModel.tvLive.observe(viewLifecycleOwner){
            if(it.results.isNotEmpty())
            view.rv_searchResults.adapter = SearchListsAdapter(getResultsLists(
                it.results.map { it.convertToSerie() },

                CardDetail.SERIE

            ), SearchListsAdapter.SeeMoreNavigation { headLists ->
                val action =
                    SearchFragmentDirections.actionSearchFragmentToViewMoreFragment(headLists)
                findNavController().navigate(action)
            })
        }

        viewModel.personLive.observe(viewLifecycleOwner){
            if(it.results.isNotEmpty())
            view.rv_searchResults.adapter = SearchListsAdapter(getResultsLists(
                it.results.map { it.convertToActor() },

                CardDetail.ACTOR

            ), SearchListsAdapter.SeeMoreNavigation { headLists ->
                val action =
                    SearchFragmentDirections.actionSearchFragmentToViewMoreFragment(headLists)
                findNavController().navigate(action)
            })
        }

        return view
    }


    fun getResultsLists(listaFilmes: List<Card>, cardInfo: Int): List<HeadLists> {
        val tipo = when(cardInfo) {
            CardDetail.FILM -> "filmes"
            CardDetail.ACTOR -> "pessoas"
            CardDetail.SERIE -> "séries"
            else -> "Outros"
        }

        val roles: HashMap<String, Int> = hashMapOf(
            "Resultados para filmes" to 0,
            "Resultados para séries" to 1,
            "Resultados para pessoas" to 2
        )

        viewModel.headLists.add(HeadLists("Resultados para $tipo", listaFilmes, cardInfo))
        viewModel.headLists.sortWith(Comparator { t, t2 ->
            return@Comparator roles[t.titleMessage]!! - roles[t2.titleMessage]!!
        })

        return viewModel.headLists.distinctBy { it.titleMessage }
    }


}
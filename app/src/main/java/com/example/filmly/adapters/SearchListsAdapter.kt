package com.example.filmly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.filmly.R
import com.example.filmly.data.model.HeadLists
import com.example.filmly.ui.search.SearchFragmentDirections
import com.example.filmly.utils.CardDetailNavigation
import com.example.filmly.utils.HeadListsDiffCallback
import com.example.filmly.utils.SeeMoreNavigation
import kotlinx.android.synthetic.main.title_and_cards_list_item.view.*

class SearchListsAdapter(
    val seeMoreNavigation: SeeMoreNavigation
) : ListAdapter<HeadLists, SearchListsAdapter.HeadSearchViewHolder>(HeadListsDiffCallback()) {

    override fun onBindViewHolder(holder: HeadSearchViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, seeMoreNavigation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadSearchViewHolder =
        HeadSearchViewHolder.from(parent)

    class HeadSearchViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            fun from(parent: ViewGroup): HeadSearchViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.cards_list_item, parent, false)
                return HeadSearchViewHolder(view)
            }
        }

        fun bind(item: HeadLists, navigation: SeeMoreNavigation) {

            view.tv_titleMessage.text = item.titleMessage
            val recyclerView = view.rv_cards

            recyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = CardsListAdapter(
                    item.cardInfo,
                    CardDetailNavigation { cardDetail ->
                        val action =
                            SearchFragmentDirections.actionSearchFragmentToCardDetailFragment(
                                cardDetail
                            )
                        findNavController().navigate(action)
                    }).also { it.submitList(item.data) }
            }

            view.tv_seeMore.setOnClickListener {
                navigation.onClick(item)
            }
        }
    }
}
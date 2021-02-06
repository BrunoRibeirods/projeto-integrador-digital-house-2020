package com.filmly.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filmly.app.R
import com.filmly.app.data.model.HeadLists
import com.filmly.app.ui.search.SearchFragmentDirections
import com.filmly.app.utils.CardDetailNavigation
import com.filmly.app.utils.HeadListsDiffCallback
import com.filmly.app.utils.SeeMoreNavigation
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
                    .inflate(R.layout.title_and_cards_list_item, parent, false)
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
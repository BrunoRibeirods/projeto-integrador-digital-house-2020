package com.example.filmly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmly.R
import com.example.filmly.data.model.HeadLists
import com.example.filmly.ui.home.HomeFragmentDirections
import com.example.filmly.utils.CardDetailNavigation
import com.example.filmly.utils.SeeMoreNavigation
import kotlinx.android.synthetic.main.title_and_cards_list_item.view.*

class HomeListsAdapter(val data: List<HeadLists>, val seeMoreNavigation: SeeMoreNavigation) :
    RecyclerView.Adapter<HomeListsAdapter.HomeListsViewHolder>() {

    override fun onBindViewHolder(holder: HomeListsViewHolder, position: Int) {
        val item = data[position]

        holder.view.tv_titleMessage.text = item.titleMessage
        val recyclerView = holder.view.rv_cards

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CardsListAdapter(
                item.cardInfo,
                CardDetailNavigation { cardDetail ->
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToCardDetailFragment(cardDetail)
                    findNavController().navigate(action)
                }).also { it.submitList(item.data) }
        }

        holder.view.tv_seeMore.setOnClickListener {
            seeMoreNavigation.onClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.title_and_cards_list_item, parent, false)
        return HomeListsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class HomeListsViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}


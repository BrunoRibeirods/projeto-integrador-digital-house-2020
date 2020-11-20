package com.example.filmly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmly.domain.Trending
import kotlinx.android.synthetic.main.title_and_cards_list_item.view.*

class HomeListsAdapter(val data: List<Trending>, val seeMoreNavigation: SeeMoreNavigation) : RecyclerView.Adapter<HomeListsAdapter.HomeListsViewHolder>() {

    override fun onBindViewHolder(holder: HomeListsViewHolder, position: Int) {
        val item = data[position]

        holder.view.tv_titleMessage.text = item.titleMessage
        val recyclerView = holder.view.rv_cards

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CardsListAdapter(item.data, CardsListAdapter.CardDetailNavigation { id ->
                val action = HomeFragmentDirections.actionHomeFragmentToCardDetailFragment(id)
                findNavController().navigate(action)
            })
            setHasFixedSize(true)
        }

        holder.view.tv_seeMore.setOnClickListener {
            seeMoreNavigation.onClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.title_and_cards_list_item, parent, false)
        return HomeListsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class HomeListsViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    class SeeMoreNavigation(val click: (trending: Trending) -> Unit) {
        fun onClick(trending: Trending) = click(trending)
    }
}


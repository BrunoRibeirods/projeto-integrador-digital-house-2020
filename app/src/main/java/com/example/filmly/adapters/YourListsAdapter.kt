package com.example.filmly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmly.R
import com.example.filmly.data.model.HeadLists
import com.example.filmly.ui.yourLists.YourListsFragmentDirections
import kotlinx.android.synthetic.main.title_and_cards_list_item.view.*

class YourListsAdapter(
    val data: List<HeadLists>,
    val seeMoreNavigation: SeeMoreNavigation
) : RecyclerView.Adapter<YourListsAdapter.HeadYourListsViewHolder>() {

    override fun onBindViewHolder(holder: HeadYourListsViewHolder, position: Int) {
        val item = data[position]

        holder.view.tv_titleMessage.text = item.titleMessage
        val recyclerView = holder.view.rv_cards

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CardsListAdapter(item.data, CardsListAdapter.CardDetailNavigation { id ->
                val action = YourListsFragmentDirections.actionYourListsFragmentToCardDetailFragment(id)
                findNavController().navigate(action)
            })
            setHasFixedSize(true)
        }

        holder.view.tv_seeMore.setOnClickListener {
            seeMoreNavigation.onClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadYourListsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.title_and_cards_list_item, parent, false)
        return HeadYourListsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class HeadYourListsViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    class SeeMoreNavigation(val click: (HeadLists) -> Unit) {
        fun onClick(headLists: HeadLists) = click(headLists)
    }

}
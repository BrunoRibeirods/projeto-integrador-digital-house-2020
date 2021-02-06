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
import com.filmly.app.ui.yourLists.YourListsFragmentDirections
import com.filmly.app.utils.CardDetailNavigation
import com.filmly.app.utils.HeadListsDiffCallback
import com.filmly.app.utils.SeeMoreNavigation
import kotlinx.android.synthetic.main.title_and_cards_list_item.view.*

class YourListsAdapter(
    val seeMoreNavigation: SeeMoreNavigation
) : ListAdapter<HeadLists, YourListsAdapter.YourListsViewHolder>(HeadListsDiffCallback()) {

    override fun onBindViewHolder(holder: YourListsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, seeMoreNavigation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YourListsViewHolder {
        return YourListsViewHolder.from(parent)
    }


    class YourListsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): YourListsViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.title_and_cards_list_item, parent, false)
                return YourListsViewHolder(view)
            }
        }

         fun bind(item: HeadLists?, navigation: SeeMoreNavigation) {
             view.tv_titleMessage.text = item?.titleMessage
             val recyclerView = view.rv_cards

             recyclerView.apply {
                 layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                 adapter =
                     item?.data?.let {
                         CardsListAdapter(item.cardInfo, CardDetailNavigation { id ->
                             val action = YourListsFragmentDirections.actionYourListsFragmentToCardDetailFragment(id)
                             findNavController().navigate(action)
                         }).also { adapter -> adapter.submitList(it) }
                     }
                 setHasFixedSize(true)
             }

             view.tv_seeMore.setOnClickListener {
                 item?.let {
                     navigation.onClick(it)
                 }
             }
         }
    }
}
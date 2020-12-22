package com.example.filmly.utils

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.filmly.data.model.Card
import com.example.filmly.data.model.CardDetail
import com.example.filmly.data.model.HeadLists

class SeeMoreNavigation(val click: (headLists: HeadLists) -> Unit) {
    fun onClick(headLists: HeadLists) = click(headLists)
}

class CardDetailNavigation(val click: (CardDetail) -> Unit) {
    fun onClick(cardDetail: CardDetail) = click(cardDetail)
}

class CardDiffCallback : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }
}

class HeadListsDiffCallback : DiffUtil.ItemCallback<HeadLists>() {
    override fun areItemsTheSame(oldItem: HeadLists, newItem: HeadLists): Boolean {
        return oldItem.cardInfo == newItem.cardInfo
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: HeadLists, newItem: HeadLists): Boolean {
        return oldItem == newItem
    }
}
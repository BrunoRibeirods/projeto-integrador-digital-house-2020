package com.digitalhouse.projetointegrador

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.projetointegrador.domain.ViewMoreCard


class FavoriteListsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_favorite_lists, container, false)

        val rc_favorite = view.findViewById<RecyclerView>(R.id.rc_favorite_lists)

        rc_favorite.adapter = ViewMoreAdapter(putListTest(15))
        rc_favorite.layoutManager = GridLayoutManager(context, 2)
        rc_favorite.setHasFixedSize(true)


        return view
    }

    fun putListTest(size: Int): List<ViewMoreCard>{
        val lista = mutableListOf<ViewMoreCard>()

        for (i in 0..size) {
            lista.add(ViewMoreCard(i,"Mr. Robot", R.drawable.filme2))
        }

        return lista
    }

    companion object {
        fun newInstance() = FavoriteListsFragment()
    }
}
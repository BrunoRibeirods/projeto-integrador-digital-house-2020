package com.digitalhouse.projetointegrador

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.projetointegrador.domain.ViewMoreCard


class ViewMoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_more, container, false)

        val rc_viewMore = view.findViewById<RecyclerView>(R.id.rc_view_more)

        rc_viewMore.adapter = ViewMoreAdapter(putListTest(15))
        rc_viewMore.layoutManager = GridLayoutManager(context, 2)
        rc_viewMore.setHasFixedSize(true)

        return view
    }

    fun putListTest(size: Int): List<ViewMoreCard>{
        val lista = mutableListOf<ViewMoreCard>()

        for (i in 0..size) {
            lista.add(ViewMoreCard(i,"Bohemian Rhapsody", R.drawable.filme))
        }

        return lista
    }

    companion object {
        fun newInstance() = ViewMoreFragment()
    }
}
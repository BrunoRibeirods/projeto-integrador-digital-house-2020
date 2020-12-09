package com.example.filmly.repository

object Repository {
    private val _homeListsOrder = mutableListOf("films", "series", "actors")
    val homeListsOrder: List<String>
        get() = _homeListsOrder

    fun addToHomeListsOrder(typeList: String) {
        _homeListsOrder.add(typeList)
    }

    fun removeFromListsOrder(typeList: String) {
        _homeListsOrder.remove(typeList)
    }
}
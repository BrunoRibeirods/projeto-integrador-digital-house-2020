package com.example.filmly.ui.profile

import androidx.lifecycle.ViewModel

class HomeListsDialogViewModel : ViewModel() {
    val genderList = mutableListOf<String>()

    fun addToGenderList(typeList: String) {
        genderList.add(typeList)
    }

    fun removeFromGenderList(typeList: String) {
        genderList.remove(typeList)
    }
}
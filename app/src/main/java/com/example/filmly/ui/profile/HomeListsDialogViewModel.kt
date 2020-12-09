package com.example.filmly.ui.profile

import androidx.lifecycle.ViewModel
import com.example.filmly.repository.Repository

class HomeListsDialogViewModel : ViewModel() {
    val genderList = Repository.homeListsOrder

    fun addToGenderList(typeList: String) {
        Repository.addToHomeListsOrder(typeList)
    }

    fun removeFromGenderList(typeList: String) {
        Repository.removeFromListsOrder(typeList)
    }
}
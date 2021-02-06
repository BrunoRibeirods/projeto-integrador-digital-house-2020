package com.filmly.app.ui.profile.dialog

import androidx.lifecycle.ViewModel
import com.filmly.app.repository.StatesRepository

class HomeListsDialogViewModel : ViewModel() {
    val genderList = StatesRepository.homeListsOrder
    val modelList = mutableListOf<String>()

    init {
        genderList.forEach {
            modelList.add(it)
        }

    }

    fun addToGenderList(typeList: String) {
        modelList.add(typeList)
    }

    fun removeFromGenderList(typeList: String) {
        modelList.remove(typeList)
    }

    fun sendHomeOrderList() {
        StatesRepository.updateHomeOrderList(modelList)
    }

    fun showChangesToast() {
        StatesRepository.showChangesToast()
    }
}
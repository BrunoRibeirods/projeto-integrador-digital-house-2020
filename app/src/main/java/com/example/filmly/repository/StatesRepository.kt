package com.example.filmly.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.filmly.data.model.UserInformation

object StatesRepository {
    private val _userInformation = MutableLiveData(UserInformation("Will", "will@filmly.com", password = "12345", birthDate = "16/07/1997"))
    val userInformation: LiveData<UserInformation>
        get() = _userInformation

    private var _homeListsOrder = mutableListOf("films", "series", "actors", "trending")
    val homeListsOrder: List<String>
        get() = _homeListsOrder

    private var _yourListsOrder = mutableListOf("films", "series", "actors")
    val yourListsOrder: List<String>
        get() = _yourListsOrder

    var searchTime = "day"
        private set

    private val _showChangesToast = MutableLiveData<Boolean>()
    val showChangesToast: LiveData<Boolean>
        get() = _showChangesToast

    fun updateSearchTime(time: Int) {
        when(time) {
            0 -> "day"
            1 -> "week"
            else -> "day"
        }.let { searchTime = it }
    }

    fun updateUserInformation(user: UserInformation) {
        if (user.name != "null") _userInformation.value?.name = user.name
        if (user.email != "null") _userInformation.value?.email = user.email
        if (user.birthDate != "null") _userInformation.value?.birthDate = user.birthDate
        if (user.password != "null") _userInformation.value?.password = user.password
    }

    fun updateHomeOrderList(list: MutableList<String>) {
        _homeListsOrder = list
    }

    fun updateYourListsOrder(list: MutableList<String>) {
        _yourListsOrder = list
    }

    fun doneShowingToast() {
        _showChangesToast.value = null
    }

    fun showChangesToast() {
        _showChangesToast.value = true
    }
}
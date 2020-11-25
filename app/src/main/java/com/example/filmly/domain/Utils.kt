package com.example.filmly.domain

import com.example.filmly.R

fun getSeries(): List<Card> {
    val serie01 = Serie(2, "Mr. Robot", R.drawable.mr_robot)
    val serie02 = Serie(9, "The Witcher", R.drawable.the_witcher)
    val serie03 = Serie(10, "Breaking Bad", R.drawable.breaking_bad)
    val serie04 = Serie(16, "Todo Mundo Odeia o Chris", R.drawable.hate_chris)
    val serie05 = Serie(17, "Grey's Anatomy", R.drawable.greys)
    return listOf(serie01, serie02, serie03, serie04, serie05)
}

fun getFilms(): List<Card> {
    val film01 = Film(1, "Bohemian Rhapsody", R.drawable.bohemian_rhapsody)
    val film02 = Film(7, "White Chicks", R.drawable.white_chicks)
    val film03 = Film(14, "Cemitério Maldito", R.drawable.cemiterio_maldito)
    val film04 = Film(15, "Annabelle", R.drawable.annabelle)
    return listOf(film01, film02, film03, film04)
}

fun getActors(): List<Actor> {
    val actor01 = Actor(3, "Rami Malek", R.drawable.rami_malek)
    val actor02 = Actor(8, "Terry Crews", R.drawable.terry_crews)
    val actor03 = Actor(11, "Henry Cavill", R.drawable.henry_cavill)
    val actor04 = Actor(12, "Bryan Cranston", R.drawable.bryan_cranston)
    val actor05 = Actor(13, "Aaron Paul", R.drawable.aaron_paul)
    return listOf(actor01, actor02, actor03, actor04, actor05)
}

fun getHorror(): List<Card> {
    val film01 = Film(14, "Cemitério Maldito", R.drawable.cemiterio_maldito)
    val film02 = Film(15, "Annabelle", R.drawable.annabelle)
    return listOf(film01, film02)
}

fun getDrama(): List<Card> {
    val serie03 = Serie(10, "Breaking Bad", R.drawable.breaking_bad)
    val serie05 = Serie(17, "Grey's Anatomy", R.drawable.greys)

    return listOf(serie03, serie05)
}
fun getComedy(): List<Card> {
    val film02 = Film(7, "White Chicks", R.drawable.white_chicks)
    val serie04 = Serie(16, "Todo Mundo Odeia o Chris", R.drawable.hate_chris)
    return listOf(film02, serie04)
}
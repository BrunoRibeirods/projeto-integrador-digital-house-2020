package com.example.filmly.domain

import com.example.filmly.R

val bohemian = "Freddie Mercury, Brian May, Roger Taylor e John Deacon formam a banda de rock Queen em 1970. Quando o estilo de vida agitado de Mercury começa a sair de controle, o grupo precisa encontrar uma forma de lidar com o sucesso e os excessos de seu líder."
val branquelas = "Dois irmãos agentes do FBI, Marcus e Kevin Copeland, acidentalmente evitam que bandidos sejam presos em uma apreensão de drogas. Como castigo, eles são forçados a escoltar um par de socialites nos Hamptons. Porém, quando as meninas descobrem o plano da agência, se recusam a ir. Sem opções, Marcus e Kevin decidem posar como as irmãs, transformando-se de homens afro-americanos em um par de loiras."
val theWitcher = "The Witcher é uma série de televisão de drama fantasia criada por Lauren Schmidt Hissrich para a Netflix. É baseado na série de livros de mesmo nome de Andrzej Sapkowski. Em 13 de novembro de 2019, a série foi renovada para segunda temporada."
val ramiMalek = "Rami Saíd Malek é um ator norte-americano. Filho de pais de origem egípcia copta, nascido e criado em Los Angeles, Malek começou sua carreira de ator com papéis coadjuvantes no cinema e na televisão, incluindo nas séries The War at Home, The Pacific e na trilogia de filmes Night at the Museum."
val terryCrews = "Terrence Alan Crews, conhecido como Terry Crews é um ator, comediante, apresentador, dançarino, ilustrador, ativista, dublador e ex-jogador de futebol americano estadunidense."

fun getSeries(): List<Card> {
    val serie01 = Serie(2, "Mr. Robot", R.drawable.mr_robot, ramiMalek)
    val serie02 = Serie(9, "The Witcher", R.drawable.the_witcher, theWitcher)
    val serie03 = Serie(10, "Breaking Bad", R.drawable.breaking_bad, "Não adicionado")
    val serie04 = Serie(16, "Todo Mundo Odeia o Chris", R.drawable.hate_chris, "Não adicionado")
    val serie05 = Serie(17, "Grey's Anatomy", R.drawable.greys, "Não adicionado")
    return listOf(serie01, serie02, serie03, serie04, serie05)
}

fun getFilms(): List<Card> {
    val film01 = Film(1, "Bohemian Rhapsody", R.drawable.bohemian_rhapsody, bohemian)
    val film02 = Film(7, "White Chicks", R.drawable.white_chicks, branquelas)
    val film03 = Film(14, "Cemitério Maldito", R.drawable.cemiterio_maldito, "Não adicionado")
    val film04 = Film(15, "Annabelle", R.drawable.annabelle, "Não adicionado")
    return listOf(film01, film02, film03, film04)
}

fun getActors(): List<Actor> {
    val actor01 = Actor(3, "Rami Malek", R.drawable.rami_malek, ramiMalek)
    val actor02 = Actor(8, "Terry Crews", R.drawable.terry_crews, terryCrews)
    val actor03 = Actor(11, "Henry Cavill", R.drawable.henry_cavill, "Não adicionado")
    val actor04 = Actor(12, "Bryan Cranston", R.drawable.bryan_cranston, "Não adicionado")
    val actor05 = Actor(13, "Aaron Paul", R.drawable.aaron_paul, "Não adicionado")
    return listOf(actor01, actor02, actor03, actor04, actor05)
}

fun getHorror(): List<Card> {
    val film01 = Film(14, "Cemitério Maldito", R.drawable.cemiterio_maldito,"Não adicionado")
    val film02 = Film(15, "Annabelle", R.drawable.annabelle,"Não adicionado")
    return listOf(film01, film02)
}

fun getDrama(): List<Card> {
    val serie03 = Serie(10, "Breaking Bad", R.drawable.breaking_bad, "Não adicionado")
    val serie05 = Serie(17, "Grey's Anatomy", R.drawable.greys, "Não adicionado")

    return listOf(serie03, serie05)
}
fun getComedy(): List<Card> {
    val film02 = Film(7, "White Chicks", R.drawable.white_chicks, branquelas)
    val serie04 = Serie(16, "Todo Mundo Odeia o Chris", R.drawable.hate_chris, "Não adicionado")
    return listOf(film02, serie04)
}
package com.example.filmly.data.model

import android.graphics.Bitmap


object FastBlur {
    fun doBlur(sentBitmap: Bitmap, radius: Int, canReuseInBitmap: Boolean): Bitmap? {

        // Stack Blur v1.0 from
        // http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
        //
        // Java Author: Mario Klingemann <mario at quasimondo.com>
        // http://incubator.quasimondo.com
        // created Feburary 29, 2004
        // Android port : Yahel Bouaziz <yahel at kayenko.com>
        // http://www.kayenko.com
        // ported april 5th, 2012

        // This is a compromise between Gaussian Blur and Box blur
        // It creates much better looking blurs than Box Blur, but is
        // 7x faster than my Gaussian Blur implementation.
        //
        // I called it Stack Blur because this describes best how this
        // filter works internally: it creates a kind of moving stack
        // of colors whilst scanning through the image. Thereby it
        // just has to add one new block of color to the right side
        // of the stack and remove the leftmost color. The remaining
        // colors on the topmost layer of the stack are either added on
        // or reduced by one, depending on if they are on the right or
        // on the left side of the stack.
        //
        // If you are using this algorithm in your code please add
        // the following line:
        //
        // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>
        val bitmap: Bitmap
        bitmap = if (canReuseInBitmap) {
            sentBitmap
        } else {
            sentBitmap.copy(sentBitmap.config, true)
        }
        if (radius < 1) {
            return null
        }
        val w = bitmap.width
        val h = bitmap.height
        val pix = IntArray(w * h)
        bitmap.getPixels(pix, 0, w, 0, 0, w, h)
        val wm = w - 1
        val hm = h - 1
        val wh = w * h
        val div = radius + radius + 1
        val r = IntArray(wh)
        val g = IntArray(wh)
        val b = IntArray(wh)
        var rsum: Int
        var gsum: Int
        var bsum: Int
        var x: Int
        var y: Int
        var i: Int
        var p: Int
        var yp: Int
        var yi: Int
        var yw: Int
        val vmin = IntArray(Math.max(w, h))
        var divsum = div + 1 shr 1
        divsum *= divsum
        val dv = IntArray(256 * divsum)
        i = 0
        while (i < 256 * divsum) {
            dv[i] = i / divsum
            i++
        }
        yi = 0
        yw = yi
        val stack = Array(div) {
            IntArray(
                3
            )
        }
        var stackpointer: Int
        var stackstart: Int
        var sir: IntArray
        var rbs: Int
        val r1 = radius + 1
        var routsum: Int
        var goutsum: Int
        var boutsum: Int
        var rinsum: Int
        var ginsum: Int
        var binsum: Int
        y = 0
        while (y < h) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            i = -radius
            while (i <= radius) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))]
                sir = stack[i + radius]
                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff
                rbs = r1 - Math.abs(i)
                rsum += sir[0] * rbs
                gsum += sir[1] * rbs
                bsum += sir[2] * rbs
                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }
                i++
            }
            stackpointer = radius
            x = 0
            while (x < w) {
                r[yi] = dv[rsum]
                g[yi] = dv[gsum]
                b[yi] = dv[bsum]
                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum
                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]
                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]
                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm)
                }
                p = pix[yw + vmin[x]]
                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff
                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]
                rsum += rinsum
                gsum += ginsum
                bsum += binsum
                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer % div]
                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]
                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]
                yi++
                x++
            }
            yw += w
            y++
        }
        x = 0
        while (x < w) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            yp = -radius * w
            i = -radius
            while (i <= radius) {
                yi = Math.max(0, yp) + x
                sir = stack[i + radius]
                sir[0] = r[yi]
                sir[1] = g[yi]
                sir[2] = b[yi]
                rbs = r1 - Math.abs(i)
                rsum += r[yi] * rbs
                gsum += g[yi] * rbs
                bsum += b[yi] * rbs
                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }
                if (i < hm) {
                    yp += w
                }
                i++
            }
            yi = x
            stackpointer = radius
            y = 0
            while (y < h) {

                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] =
                    -0x1000000 and pix[yi] or (dv[rsum] shl 16) or (dv[gsum] shl 8) or dv[bsum]
                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum
                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]
                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]
                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w
                }
                p = x + vmin[y]
                sir[0] = r[p]
                sir[1] = g[p]
                sir[2] = b[p]
                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]
                rsum += rinsum
                gsum += ginsum
                bsum += binsum
                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer]
                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]
                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]
                yi += w
                y++
            }
            x++
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h)
        return bitmap
    }
}










/*
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

 */
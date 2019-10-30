package com.example.myapplication

import com.example.myapplication.data.Game

interface GameAdaptaterInterface {
    val games: Array<Game>
    fun open(game: Game): Unit
}
package com.se2gruppe5.risikofrontend.game.dataclasses

data class TerritoryRecord (val id: Int, var troops: Int){
    var owner: PlayerRecord? = null
}
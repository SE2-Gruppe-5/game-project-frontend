package com.se2gruppe5.risikofrontend.game.dice.diceModels

import kotlin.random.Random

/**
 * A fair six-sided die
 * Returns a number 1-6
 */
class Dice1d6Generic : IDice {
    override fun roll(): Int {
        return Random.Default.nextInt(1, 6 + 1)
    }
}
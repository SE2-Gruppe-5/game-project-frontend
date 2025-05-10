package com.se2gruppe5.risikofrontend.game

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.lifecycle.lifecycleScope
import com.se2gruppe5.risikofrontend.R
import com.se2gruppe5.risikofrontend.game.dataclasses.PlayerRecord
import com.se2gruppe5.risikofrontend.game.dataclasses.TerritoryRecord
import com.se2gruppe5.risikofrontend.game.dice.DiceVisualAndroid
import com.se2gruppe5.risikofrontend.game.dice.diceModels.Dice1d6Generic
import com.se2gruppe5.risikofrontend.game.managers.TerritoryManager
import com.se2gruppe5.risikofrontend.game.territory.ITerritoryVisual
import com.se2gruppe5.risikofrontend.game.territory.PointingArrowAndroid
import com.se2gruppe5.risikofrontend.game.territory.TerritoryVisualAndroid
import com.se2gruppe5.risikofrontend.network.NetworkClient
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {

    private lateinit var networkClient: NetworkClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.game)

        networkClient = NetworkClient()


//-------------------------------------- <Placeholder>
        val diceBtn = this.findViewById<ImageButton>(R.id.diceButton)
        val diceTxt = this.findViewById<TextView>(R.id.diceText)
        val diceVisualAndroid = DiceVisualAndroid(Dice1d6Generic(), diceBtn, diceTxt)
        diceVisualAndroid.clickSubscription { it.roll() }

        val p1 = PlayerRecord(1, "Markus", Color.rgb(255, 100, 0))
        val p2 = PlayerRecord(2, "Leo", Color.rgb(0, 100, 255))
        val players = listOf(p1, p2)
        val nameToPlayer = players.associateBy { it.name }


        //todo This is not pretty and hardcoded. It shouldn't be. It should be done by the GameManager
        lifecycleScope.launch {
            try {
                val assignments = networkClient.fetchTerritoryAssignments()
                val troopDistributions = networkClient.fetchTroopDistribution()

                if (assignments != null && troopDistributions != null) {
                    // ----- Territory A
                    val territoryName1 = "Territory1"
                    val ownerName1 =
                        assignments.entries.first { it.value.contains(territoryName1) }.key
                    val owner1 = nameToPlayer[ownerName1]!!
                    val troops1 = troopDistributions[ownerName1]!![territoryName1]!!

                    val t1 = TerritoryRecord(1, troops1).apply { this.owner = owner1 }
                    val t1_txt = findViewById<TextView>(R.id.territoryAtext)
                    val t1_btn = findViewById<ImageButton>(R.id.territoryAbtn)
                    val t1_outline = findViewById<View>(R.id.territoryAoutline)
                    val t1_vis: ITerritoryVisual =
                        TerritoryVisualAndroid(t1, t1_txt, t1_txt, t1_btn, t1_outline)
                    t1_vis.changeStat(troops1)

                    // ----- Territory B
                    val territoryName2 = "Territory2"
                    val ownerName2 =
                        assignments.entries.first { it.value.contains(territoryName2) }.key
                    val owner2 = nameToPlayer[ownerName2]!!
                    val troops2 = troopDistributions[ownerName2]!![territoryName2]!!

                    val t2 = TerritoryRecord(2, troops2).apply { this.owner = owner2 }
                    val t2_txt = findViewById<TextView>(R.id.territoryBtext)
                    val t2_btn = findViewById<ImageButton>(R.id.territoryBbtn)
                    val t2_outline = findViewById<View>(R.id.territoryBoutline)
                    val t2_vis: ITerritoryVisual =
                        TerritoryVisualAndroid(t2, t2_txt, t2_txt, t2_btn, t2_outline)
                    t2_vis.changeStat(troops2)

                    // TerritoryManager und Pfeil
                    val pointingArrow =
                        PointingArrowAndroid(this@GameActivity, "#FF0000".toColorInt(), 15f).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                        }
                    findViewById<ViewGroup>(R.id.main).addView(pointingArrow)

                    TerritoryManager.init(p1, pointingArrow)
                    val territoryManager = TerritoryManager.get()
                    territoryManager.addTerritory(t1_vis)
                    territoryManager.addTerritory(t2_vis)
                    territoryManager.assignOwner(t1_vis, owner1)
                    territoryManager.assignOwner(t2_vis, owner2)

                    findViewById<ImageButton>(R.id.goattack).setOnClickListener {
                        territoryManager.enterSelectMode()
                        territoryManager.enterAttackMode()
                        it.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
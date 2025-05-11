package com.se2gruppe5.risikofrontend.lobby

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.ImageButton
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.se2gruppe5.risikofrontend.R
import com.se2gruppe5.risikofrontend.network.INetworkClient
import com.se2gruppe5.risikofrontend.network.NetworkClient
import com.se2gruppe5.risikofrontend.network.sse.SseClientService
import com.se2gruppe5.risikofrontend.network.sse.constructServiceConnection
import com.se2gruppe5.risikofrontend.startmenu.MenuActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class JoinLobbyActivity :AppCompatActivity() {
    val client = NetworkClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        enableEdgeToEdge()
        setContentView(R.layout.joinlobby)

        val backBtn = this.findViewById<ImageButton>(R.id.backBtn)
        backBtn.setOnClickListener({
            Log.i("NAVIGATION", "Quit lobby")
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        })

        val joinBtn = this.findViewById<Button>(R.id.joinLobbyBtn)
        val nameInput = findViewById<EditText>(R.id.name_input)
        val joinInput = findViewById<EditText>(R.id.join_input)
        joinBtn.setOnClickListener({
            val name = nameInput.text.toString()
            val joincode = joinInput.text.toString()

            Log.i("NAVIGATION", "Create lobby")
            val intent = Intent(this, LobbyActivity::class.java)
            intent.putExtra("PLAYER_NAME", name)
            intent.putExtra("LOBBY_CODE", joincode)
            startActivity(intent)
        })
    }

    }

package com.se2gruppe5.risikofrontend

import android.os.Bundle
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.se2gruppe5.risikofrontend.createLobby.CreateLobbyScreen
import com.se2gruppe5.risikofrontend.styles.StyledButton


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}


@Composable
fun HomeScreen() {
    val darkBackground = Color(0xFF121212)
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Risc",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontSize = 40.sp
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            StyledButton(
                text = "Create Lobby",
                onClick = { val intent = Intent(context, CreateLobbyScreen()::class.java)
                    context.startActivity(intent) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            StyledButton(
                text = "Join Lobby",
                onClick = { /* TODO: Navigate or join lobby */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            StyledButton(
                text = "Tutorial",
                onClick = { /* TODO: Navigate to tutorial */ }
            )
        }
        Text(
            text = "By SE2 Gruppe 5",
            style = MaterialTheme.typography.labelLarge.copy(color = Color.White),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
        )
    }
}
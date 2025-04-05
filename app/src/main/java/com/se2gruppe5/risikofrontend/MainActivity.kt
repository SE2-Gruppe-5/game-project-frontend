package com.se2gruppe5.risikofrontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle





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
fun StyledButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge.copy(
        color = Color.White,
        fontSize = 24.sp
    )
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        modifier = modifier
            .fillMaxWidth(fraction = 0.3f)
            .height(56.dp)
    ) {
        Text(text = text, style = textStyle)
    }
}

@Composable
fun HomeScreen() {
    val darkBackground = Color(0xFF121212)

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
                onClick = { /* TODO: Navigate or create lobby */ }
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
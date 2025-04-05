package com.se2gruppe5.risikofrontend.styles

import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle

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

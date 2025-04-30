package com.example.scribbledash.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ScreenHeader(
    title: String,
    subtitle: String,
    titleColor: Color = Color(0xFF514437),
    subtitleColor: Color = Color(0xFF7F7163),
    titleStyle: TextStyle = MaterialTheme.typography.displayMedium,
    subtitleStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    spacerHeight: Dp = 8.dp,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = horizontalAlignment,
        modifier = modifier
    ) {
        Text(
            text = title,
            style = titleStyle,
            color = titleColor
        )
        Spacer(modifier = Modifier.height(spacerHeight))
        Text(
            text = subtitle,
            style = subtitleStyle,
            color = subtitleColor
        )
    }
}
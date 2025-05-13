package com.example.scribbledash.features.drawscreen.presentation.components

import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.scribbledash.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding


@Composable
fun DrawBottomActions(
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onClear: () -> Unit,
    onDone: () -> Unit,
    hasStrokes: Boolean = false,
    canUndo: Boolean = true,
    canRedo: Boolean = true,
    canClear: Boolean = true,
    showDone: Boolean = false

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Undo Button
        Row{
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.4f))
                    .clickable(enabled = canUndo, onClick = onUndo),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.reply_icon),
                    contentDescription = "Undo",
                    modifier = Modifier
                        .size(28.dp),
                    tint = if (canUndo)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            // Redo Button
            Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.4f))
                        .clickable(enabled = canRedo, onClick = onRedo),
            contentAlignment = Alignment.Center
            ) {
            Icon(
                painter = painterResource(R.drawable.forward_icon),
                contentDescription = "Redo",
                modifier = Modifier
                    .size(28.dp),
                tint = if (canRedo)
                    MaterialTheme.colorScheme.onSurface
                else
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            )
        } }
        // Done Button
        Box(
            modifier = Modifier
                .height(64.dp)
                .width(112.dp)
                .clip(RoundedCornerShape(22.dp))
                .border(5.dp, MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(22.dp))
                .background(
                    if (hasStrokes) Color(0xFF4CAF50) // Green color when strokes exist
                    else MaterialTheme.colorScheme.surfaceContainerLowest
                )
                .clickable(onClick = onDone),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Done!",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}
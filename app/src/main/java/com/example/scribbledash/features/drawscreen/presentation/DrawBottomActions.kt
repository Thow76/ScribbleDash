package com.example.scribbledash.features.drawscreen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.scribbledash.R

@Composable
fun DrawBottomActions(
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onClearCanvas: () -> Unit,
    isUndoEnabled: Boolean,
    isRedoEnabled: Boolean,
    isClearEnabled: Boolean
) {
    NavigationBar(
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationBarItem(
                selected = false,
                onClick = onUndo,
                icon = {
                    // Undo Icon
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.4f))
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.reply_icon),
                            contentDescription = "Undo",
                            modifier = Modifier
                                .size(28.dp)
                                .background(Color.Transparent)
                                .align(Alignment.Center)
                        )
                    }
                },
                enabled = isUndoEnabled,
            )
            NavigationBarItem(
                selected = false,
                onClick = onRedo,
                icon = {
                    // Redo Icon
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.4f))
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.forward_icon),
                            contentDescription = "Redo",
                            modifier = Modifier
                                .size(28.dp)
                                .background(Color.Transparent)
                                .align(Alignment.Center)
                        )
                    }
                },
                enabled = isRedoEnabled,
            )
            NavigationBarItem(
                modifier = Modifier.weight(3f),
                selected = false,
                onClick = onClearCanvas,
                icon = {
                    // Clear Icon
                    Box(
                        modifier = Modifier
                            .height(64.dp)
                            .width(210.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .border(
                                width = 5.dp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                shape = RoundedCornerShape(22.dp)
                            )
                            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                    ) {
                        Text(
                            text = "Clear Canvas",
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                },
                enabled = isClearEnabled,
            )
        }
    }
}
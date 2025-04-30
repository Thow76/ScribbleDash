package com.example.scribbledash.core.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.scribbledash.R

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun CustomTopAppBar(
    onClose: () -> Unit,
    showCloseButton: Boolean = true,
    title: @Composable (() -> Unit)? = null,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        actions = {
            if (showCloseButton) {
                IconButton(onClick = onClose) {
                    Icon(
                        painter = painterResource(R.drawable.close_circle_icon),
                        contentDescription = "Close",
                        modifier = Modifier.size(28.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        },
        title = { title?.invoke() }
    )
}
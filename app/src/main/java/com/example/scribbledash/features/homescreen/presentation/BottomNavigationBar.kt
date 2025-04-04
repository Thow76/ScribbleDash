package com.example.scribbledash.features.homescreen.presentation

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.scribbledash.R

@Composable
fun BottomNavigationBar(
    onHomeClicked: () -> Unit,
    onOtherClicked: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = false, // or track selection state
            onClick = { onOtherClicked() },
            icon = { Image(painter = painterResource(id = R.drawable.chart_icon), contentDescription = null)
            }
        )
        NavigationBarItem(
            selected = true,
            onClick = { onHomeClicked() },
            icon = { Image(
                painter = painterResource(
                    id = R.drawable.home_icon),
                contentDescription = null,
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(androidx.compose.material3.MaterialTheme.colorScheme.primary
                        )
            ) }
        )
    }
}
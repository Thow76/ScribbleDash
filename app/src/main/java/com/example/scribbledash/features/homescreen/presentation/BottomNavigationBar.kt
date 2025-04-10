package com.example.scribbledash.features.homescreen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scribbledash.R
import com.example.scribbledash.navigation.Screen

@Composable
fun BottomNavigationBar(
    navController: NavController,

    ) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Row {
            NavigationBarItem(
                selected = false, // or track selection state
                onClick = { },
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.chart_icon),
                        contentDescription = null
                    )
                }
            )
            NavigationBarItem(
                    selected = false, // or track selection state
            onClick = { navController.navigate(Screen.Home.route)},
            icon = { Image(
                painter = painterResource(
                    id = R.drawable.home_icon),
                contentDescription = null,
            ) }
            )
        }

    }
}
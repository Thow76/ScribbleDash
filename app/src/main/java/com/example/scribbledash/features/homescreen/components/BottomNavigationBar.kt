package com.example.scribbledash.features.homescreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.scribbledash.R
import com.example.scribbledash.navigation.Screen

@Composable
fun BottomNavigationBar(
    navController: NavController,

    ) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
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
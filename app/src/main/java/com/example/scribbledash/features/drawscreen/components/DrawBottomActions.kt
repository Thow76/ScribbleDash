//package com.example.scribbledash.features.drawscreen.presentation.components
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import com.example.scribbledash.R
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.navigationBarsPadding
//
//
//@Composable
//fun DrawBottomActions(
//    onUndo: () -> Unit,
//    onRedo: () -> Unit,
//    onClear: () -> Unit,
//    canUndo: Boolean = true,
//    canRedo: Boolean = true,
//    canClear: Boolean = true
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//            .navigationBarsPadding(),
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        // Undo Button
//        Box(
//            modifier = Modifier
//                .size(64.dp)
//                .clip(RoundedCornerShape(22.dp))
//                .background(MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.4f))
//                .clickable(enabled = canUndo, onClick = onUndo),
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(
//                painter = painterResource(R.drawable.reply_icon),
//                contentDescription = "Undo",
//                modifier = Modifier
//                    .size(28.dp),
//                tint = if (canUndo)
//                    MaterialTheme.colorScheme.onSurface
//                else
//                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
//            )
//        }
//
//        // Redo Button
//        Box(
//            modifier = Modifier
//                .size(64.dp)
//                .clip(RoundedCornerShape(22.dp))
//                .background(MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.4f))
//                .clickable(enabled = canRedo, onClick = onRedo),
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(
//                painter = painterResource(R.drawable.forward_icon),
//                contentDescription = "Redo",
//                modifier = Modifier
//                    .size(28.dp),
//                tint = if (canRedo)
//                    MaterialTheme.colorScheme.onSurface
//                else
//                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
//            )
//        }
//
//        // Clear Canvas Button
//        Box(
//            modifier = Modifier
//                .height(64.dp)
//                .width(201.dp)
//                .clip(RoundedCornerShape(22.dp))
//                .border(
//                    width = 5.dp,
//                    color = MaterialTheme.colorScheme.onPrimary,
//                    shape = RoundedCornerShape(22.dp)
//                )
//                .background(
//                    if (canClear)
//                        MaterialTheme.colorScheme.outline
//                    else
//                        MaterialTheme.colorScheme.surfaceContainerLowest.copy(alpha = 0.7f)
//                )
//                .clickable(enabled = canClear, onClick = onClear),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = "Clear Canvas",
//                color = MaterialTheme.colorScheme.onPrimary,
//                style = MaterialTheme.typography.headlineMedium
//            )
//        }
//    }
//}

package com.example.scribbledash.features.drawscreen.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.scribbledash.R

@Composable
fun DrawBottomActions(
    modifier: Modifier = Modifier,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onClear: () -> Unit,
    canUndo: Boolean = true,
    canRedo: Boolean = true,
    canClear: Boolean = true,
    showDone: Boolean = false,
    onDone: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .navigationBarsPadding(),
        horizontalArrangement = spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionIconButton(
            iconRes     = R.drawable.reply_icon,
            contentDesc = stringResource(R.string.undo),
            enabled     = canUndo,
            onClick     = onUndo
        )

        ActionIconButton(
            iconRes     = R.drawable.forward_icon,
            contentDesc = stringResource(R.string.redo),
            enabled     = canRedo,
            onClick     = onRedo
        )

        // Here: if showDone==false we give the Clear button weight so it expands
        val clearModifier = if (!showDone) Modifier.weight(1f) else Modifier
        ActionTextButton(
            modifier = clearModifier,
            text     = stringResource(R.string.clear_canvas),
            enabled  = canClear,
            onClick  = onClear
        )

        if (showDone) {
            Spacer(Modifier.width(16.dp))
            ActionTextButton(
                text    = stringResource(R.string.done),
                enabled = canClear,
                onClick = onDone
            )
        }
    }
}

@Composable
private fun ActionIconButton(
    @DrawableRes iconRes: Int,
    contentDesc: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.4f))
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter           = painterResource(iconRes),
            contentDescription = contentDesc,
            modifier          = Modifier.size(28.dp),
            tint              = if (enabled)
                MaterialTheme.colorScheme.onSurface
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    }
}

@Composable
private fun ActionTextButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(64.dp)
            .clip(RoundedCornerShape(22.dp))
            .border(
                width = 5.dp,
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(22.dp)
            )
            .background(
                if (enabled)
                    MaterialTheme.colorScheme.outline
                else
                    MaterialTheme.colorScheme.surfaceContainerLowest.copy(alpha = 0.7f)
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text  = text,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}


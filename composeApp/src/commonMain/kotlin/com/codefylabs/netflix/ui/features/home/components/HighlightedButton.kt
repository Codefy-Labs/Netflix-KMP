package com.codefylabs.netflix.ui.features.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Download
import netflix_kmp.composeapp.generated.resources.Res
import netflix_kmp.composeapp.generated.resources.download
import netflix_kmp.composeapp.generated.resources.info
import netflix_kmp.composeapp.generated.resources.my_list
import netflix_kmp.composeapp.generated.resources.play
import org.jetbrains.compose.resources.stringResource

@Composable
fun MyListButton(
    modifier: Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.selectable(selected = false, onClick = {})
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(Res.string.my_list),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 10.sp,
            ),
            maxLines = 1
        )
    }
}

@Composable
fun InfoButton(
    modifier: Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.selectable(selected = false, onClick = {})
    ) {
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(Res.string.info),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 10.sp,
            ),
            maxLines = 1
        )
    }
}

@Composable
fun PlayButton(
    isPressed: MutableState<Boolean>,
    modifier: Modifier,
    cornerPercent: Int = 8,
) {
    Button(
        onClick = { isPressed.value = isPressed.value.not() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(cornerPercent),
        modifier = modifier
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(Res.string.play),
                style = MaterialTheme.typography.bodyLarge.copy(
                    letterSpacing = (-0.05).sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
                maxLines = 1
            )
        }
    }
}

@Composable
fun DownloadButton(
    isPressed: MutableState<Boolean>,
    modifier: Modifier,
    cornerPercent: Int = 8,
) {
    Button(
        onClick = { isPressed.value = isPressed.value.not() },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceBright
        ),
        shape = RoundedCornerShape(cornerPercent),
        modifier = modifier
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = FeatherIcons.Download,
                tint = Color.White,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(Res.string.download),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.05).sp,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )
        }
    }
}
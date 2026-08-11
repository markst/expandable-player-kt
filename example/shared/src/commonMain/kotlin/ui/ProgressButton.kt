package ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.ScOrange
import kotlin.math.min

@Composable
fun ProgressButton() {
    var progress by remember { mutableStateOf(0.4f) }

    Box(
        modifier = Modifier
            .aspectRatio(ratio = 1.0f)
            .clip(CircleShape)
            .background(Color.White)
            .clickable {
                progress = min(progress + 0.1f, 1f)
            },
        contentAlignment = Alignment.Center
    ) {
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = tween(durationMillis = 200, easing = LinearEasing)
        )

        CircularProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier
                .size(45.dp),
            strokeWidth = 4.dp,
            color = Color.ScOrange,
            backgroundColor = Color.Black
        )

        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
    }
}

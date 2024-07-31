package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PlayButton() {
// Uncaught Kotlin exception: kotlin.native.internal.IrLinkageError: Function 'clickable' can not be called: No function found for symbol 'androidx.compose.foundation/clickable|clickable@androidx.compose.ui.Modifier(androidx.compose.foundation.interaction.MutableInteractionSource;androidx.compose.foundation.Indication?;kotlin.Boolean;kotlin.String?;androidx.compose.ui.semantics.Role?;kotlin.Function0<kotlin.Unit>){}[0]'
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(70.dp)
            .aspectRatio(1f)
            .background(color = Color.Black, shape = CircleShape)
    ) {
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = "Play",
            tint = Color.White,
            modifier = Modifier.size(40.dp)
        )
    }
}

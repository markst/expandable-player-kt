package dev.markturnip.expandable

import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

fun Modifier.expandable(
    handler: MinimizableHandler,
    scope: CoroutineScope
): Modifier = composed {
    val density = LocalDensity.current

    // Derive values from fraction
    val height = (handler.settings.minimizedHeight + (handler.settings.maximizedHeight - handler.settings.minimizedHeight) * handler.fraction.value)
    val alpha = 1f - handler.fraction.value
    val radius = handler.settings.cornerRadius - (20 * handler.fraction.value).dp
    val decay = splineBasedDecay<Float>(density)

    this
        .padding(horizontal = handler.horizontalPadding)
        .padding(bottom = handler.bottomPadding)
        .height(height)
        .border(
            width = 1.dp,
            color = Color.White.copy(alpha = alpha),
            shape = RoundedCornerShape(radius)
        )
        .clip(RoundedCornerShape(radius))
        .pointerInput(Unit) {
            detectTapGestures {
                scope.launch {
                    // Interrupt any ongoing animation.
                    handler.fraction.stop()
                    handler.toggle(animated = true)
                }
            }
        }
        .draggable(
            orientation = Orientation.Vertical,
            reverseDirection = true,
            state = rememberDraggableState { delta ->
                val dragAmountDp = with(density) { delta.toDp() }
                val totalDragRange = handler.settings.maximizedHeight - handler.settings.minimizedHeight
                val dragFraction = dragAmountDp / totalDragRange
                scope.launch {
                    handler.fraction.snapTo((handler.fraction.value + dragFraction).coerceIn(0f, 1f))
                }
            },
            onDragStarted = {
                // Interrupt any ongoing animation.
                handler.fraction.stop()
            },
            onDragStopped = { velocity ->
                if (velocity.absoluteValue < 100) {
                    if (handler.fraction.value < 0.4f) {
                        handler.collapse(animated = true)
                    } else {
                        handler.expand(animated = true)
                    }
                } else {
                    handler.fraction.updateBounds(
                        lowerBound = 0.0f,
                        upperBound = 1.0f
                    )
                    // Velocity is in pixels per second:
                    val newVelocity = velocity / handler.settings.maximizedHeight.value
                    if (velocity > 0.0) {
                        handler.expand(animated = true, velocity = newVelocity)
                    } else {
                        handler.collapse(animated = true)
                        // TODO: Add `canReachTargetWithDecay` in order to conditionally animate
                        // handler.fraction.animateDecay(velocity, decay)
                    }
                }
            }
        )
}
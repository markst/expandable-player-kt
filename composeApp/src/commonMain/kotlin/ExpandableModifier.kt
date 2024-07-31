import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Modifier.expandable(
    handler: MinimizableHandler,
    scope: CoroutineScope
): Modifier = composed {
    val density = LocalDensity.current

    // Derive values from fraction
    val height = (handler.settings.minimizedHeight + (handler.settings.maximizedHeight - handler.settings.minimizedHeight) * handler.fraction.value)
    val alpha = 1f - handler.fraction.value
    val radius = handler.settings.cornerRadius - (20 * handler.fraction.value).dp

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
        .pointerInput(Unit) {
            val velocityTracker = VelocityTracker()
            val decay = splineBasedDecay<Float>(this)
            detectVerticalDragGestures(
                onDragStart = {
                    scope.launch {
                        // Interrupt any ongoing animation.
                        handler.fraction.stop()
                    }
                },
                onVerticalDrag = { change, dragAmount ->
                    val dragAmountDp = with(density) { -dragAmount.toDp() }
                    val totalDragRange = handler.settings.maximizedHeight - handler.settings.minimizedHeight
                    val dragFraction = dragAmountDp / totalDragRange
                    scope.launch {
                        handler.fraction.snapTo((handler.fraction.value + dragFraction).coerceIn(0f, 1f))
                    }
                    velocityTracker.addPosition(change.uptimeMillis, change.position)
                    change.consume()  // Mark the event as consumed after processing it
                },
                onDragEnd = {
                    val velocity = velocityTracker.calculateVelocity().y
                    // TODO: Resolve velocity calculation:
                    val targetValue = decay.calculateTargetValue(height.value, velocity)
                    val midHeight = (handler.settings.minimizedHeight.value + (handler.settings.maximizedHeight.value * 0.5))

                    if (handler.fraction.value < 0.3f) {
                        handler.collapse(animated = true)
                    } else {
                        handler.expand(animated = true)
                    }
                },
                onDragCancel = {
                    // Collapse if cancel?
                }
            )
        }
}

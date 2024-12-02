package dev.markturnip.expandable

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MinimizableHandler(
    private val scope: CoroutineScope,
    val settings: ExpandableSettings
) {
    private var expanded by mutableStateOf(false)

    val fraction = Animatable(0.0f)
    val transparency: Float
        get() = fraction.value
    val horizontalPadding: Dp
        get() = (10 * (1.0 - fraction.value)).dp
    val bottomPadding: Dp
        get() = settings.bottomPadding + (settings.expandedBottomPadding.value * fraction.value).dp

    fun expand(animated: Boolean, velocity: Float = 0.0f) {
        toggle(expand = true, animated = animated)
    }

    fun collapse(animated: Boolean) {
        toggle(expand = false, animated = animated)
    }

    fun toggle(animated: Boolean) {
        toggle(expand = !expanded, animated = animated)
    }

    private fun toggle(expand: Boolean, animated: Boolean, velocity: Float = 0.0f) {
        scope.launch {
            expanded = expand
            val targetFraction = if (expand) 1.0f else 0.0f
            if (animated) {
                fraction.animateTo(targetFraction, initialVelocity = velocity)
            } else {
                fraction.snapTo(targetFraction)
            }
        }
    }
}
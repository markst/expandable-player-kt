import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
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

    fun toggle(animated: Boolean) {
        if (expanded) {
            collapse(animated = animated)
        } else {
            expand(animated = animated)
        }
    }

    fun expand(animated: Boolean) {
        scope.launch {
            expanded = true
            val targetFraction = 1f
            if (animated) {
                fraction.animateTo(targetFraction)
            } else {
                fraction.snapTo(targetFraction)
            }
        }
    }

    fun collapse(animated: Boolean) {
        scope.launch {
            expanded = false
            val targetFraction = 0f
            if (animated) {
                fraction.animateTo(targetFraction)
            } else {
                fraction.snapTo(targetFraction)
            }
        }
    }
}
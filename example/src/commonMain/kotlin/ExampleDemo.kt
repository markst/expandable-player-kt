import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.ScGray
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import expandableplayer.example.generated.resources.Res
import expandableplayer.example.generated.resources.screengrab_1
import org.jetbrains.compose.resources.painterResource

import dev.markturnip.expandable.ExpandableSettings
import dev.markturnip.expandable.MinimizableHandler
import dev.markturnip.expandable.expandable
import ui.PlayerView

@Composable
fun ExampleDemo() {
    val scope = rememberCoroutineScope()
    val settings = ExpandableSettings(
        minimizedHeight = 70.dp,
        maximizedHeight = 650.dp, // Use screen height minus min?
        bottomPadding = 100.dp,
        expandedBottomPadding = 8.dp,
        cornerRadius = 35.dp
    )
    val handler = remember { MinimizableHandler(scope, settings) }
    val hazeState = remember { HazeState() }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(Res.drawable.screengrab_1),
            contentDescription = "1",
            modifier = Modifier
                .wrapContentHeight(unbounded = true, align = Alignment.Top)
                .fillMaxWidth()
                .haze(state = hazeState)

        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.ScGray.copy(alpha = handler.transparency))
        )

        PlayerView(
            modifier = Modifier
                .fillMaxWidth()
                .expandable(handler = handler, scope = scope)
                .hazeChild(
                    state = hazeState,
                    style = HazeDefaults.style(
                        blurRadius = 18.dp,
                        backgroundColor = Color.Gray.copy(0.6f)
                    )
                ),
            miniHandler = handler
        )

    }
}
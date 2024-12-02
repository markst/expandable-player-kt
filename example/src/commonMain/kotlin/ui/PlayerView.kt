package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.markturnip.expandable.MinimizableHandler
import expandableplayer.example.generated.resources.*
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlayerView(modifier: Modifier = Modifier, miniHandler: MinimizableHandler) {
    val title = "Menage Backburners 2 Snippets"
    val detail = "Ménage"
    val behindTrack = "Behind this track"

    Box(
        contentAlignment =  Alignment.TopCenter,
        modifier = modifier
            .fillMaxWidth()
            .clipToBounds()
    ) {
        Image(
            painter = painterResource(Res.drawable.album_art),
            contentDescription = "1",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .wrapContentSize(unbounded = true, align = Alignment.TopCenter)
                .height(miniHandler.settings.maximizedHeight)
                .fillMaxWidth()
                .alpha(4.0f * miniHandler.fraction.value)
                .clipToBounds()
        )

        Column(
            modifier = Modifier
                .wrapContentHeight(unbounded = true, align = Alignment.Top)
                .height(miniHandler.settings.maximizedHeight)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(4.0f * miniHandler.fraction.value)
                    .padding(20.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        title,
                        color = Color.White,
                        modifier = Modifier.background(Color.Black),
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(detail, color = Color.White, modifier = Modifier.background(Color.Black))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        behindTrack,
                        color = Color.White,
                        modifier = Modifier.background(Color.Black)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                DownChevronButton(onClick = {
                    miniHandler.toggle(animated = true)
                })
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                PlayButton()
                Spacer(modifier = Modifier.weight(1f))
                ProgressBar("0:12:34", "1:12:45", 0.3f)
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        // Top mini player:
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .height(miniHandler.settings.minimizedHeight)
                .padding(14.dp)
                .alpha(1.0f - (4.0f * miniHandler.fraction.value))
                .fillMaxWidth()
        ) {
            ProgressButton()
            Column(
                verticalArrangement = Arrangement.spacedBy(-6.dp),
                modifier = Modifier
                    .background(Color.Transparent)
            ) {
                Text(
                    title,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.subtitle1
                )
                Text(detail, color = Color.Black)
            }
        }
    }
}
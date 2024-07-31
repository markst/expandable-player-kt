import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import example.ui.ExampleDemo

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold(
            contentWindowInsets = WindowInsets.navigationBars
        ) {
            ExampleDemo()
        }
    }
}
# Expandable Player Modifier

[![Platform](https://img.shields.io/badge/platform-Android-blue.svg)](https://github.com/markst)  
A custom Jetpack Compose modifier that adds an expandable media player to your screen, inspired by SoundCloud's mini-player.

## Features

- Supports vertical dragging to expand/collapse the player.
- Customizable height, corner radius, and padding for minimized and maximized states.
- Smooth animations for transitions.
- Fully composable and integrates seamlessly with Jetpack Compose UI.

## Demo

<table>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/2507ec28-9baa-436e-bdbc-1a79297a1553" width="320"/></td>
    <td><img src="https://github.com/user-attachments/assets/46e89b32-d9de-42b9-9d63-befd2a8e5c1d" width="320"/></td>
  </tr>
</table>

## Usage

Here's an example of how to use the `expandable` modifier in your Composable:

```kotlin
@Composable
fun ExampleDemo() {
    val scope = rememberCoroutineScope()
    val settings = ExpandableSettings(
        minimizedHeight = 70.dp,
        maximizedHeight = 650.dp,
        bottomPadding = 100.dp,
        expandedBottomPadding = 8.dp,
        cornerRadius = 35.dp
    )
    val handler = remember { MinimizableHandler(scope, settings) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Your content goes here
        PlayerView(
            modifier = Modifier
                .fillMaxWidth()
                .expandable(handler = handler, scope = scope),
            miniHandler = handler
        )
    }
}
```

## Settings

Customize the behavior using `ExpandableSettings`:

```kotlin
data class ExpandableSettings(
    val minimizedHeight: Dp,
    val maximizedHeight: Dp,
    val bottomPadding: Dp,
    val expandedBottomPadding: Dp,
    val cornerRadius: Dp
)
```

## How It Works

The `expandable` modifier responds to gestures and adjusts its height, corner radius, and alpha based on the expansion state. You can expand or collapse the player programmatically using the `MinimizableHandler`.

## License

This project is licensed under the MIT License. See the LICENSE file for details.

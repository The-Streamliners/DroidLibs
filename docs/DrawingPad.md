# DrawingPad

DrawingPad is a composable that allows user to draw on it. Common use cases include taking a signature input. It has built-in support for capturing the input as a Bitmap using our Capturable composable.

<img src="assets/DrawingPadSample.gif" title="" alt="" width="250">

## Step 1 : Define State

```kotlin
val drawingPadState = rememberDrawingPadState() // Returns DrawingPadState
```

## Step 2 : Composable

```kotlin
@Composable
fun DrawingPad(
    modifier: Modifier,
    state: DrawingPadState,

    // Customizations
    showClearButton: Boolean = true,
    clearButtonIcon: ImageVector = Icons.Default.Refresh,
    clearButtonPadding: Dp = 12.dp,
    clearButtonAlignment: Alignment = Alignment.TopEnd
)
```

Example :

```kotlin
DrawingPad(
    modifier = Modifier.fillMaxSize(),
    state = drawingPadState
)
```

## Step 3 : Capture

Use the capture function to capture the drawing as Bitmap :

```kotlin
fun DrawingPadState.capture(
    callback: (Bitmap) -> Unit // Use the bitmap here
)
```

Example :

```kotlin
FloatingActionButton(
    onClick = {
        drawingPadState.capture { bitmap ->
            saveAndShareImage(bitmap)
        }
    }
) {
    Icon(
        imageVector = Icons.Default.Share,
        contentDescription = "Capture & Share"
    )
}
```

## Check if drawing is blank

To check if the drawing is blank, use the DrawingPadState#isBlank() function :

```kotlin
fun DrawingPadState.isBlank(): Boolean
```

Example :

```kotlin
if (drawingPadState.isBlank()) {
    showToast("Please draw something!")
} else {
    drawingPadState.capture { bitmap ->
        saveAndShareImage(bitmap)
    }
}
```

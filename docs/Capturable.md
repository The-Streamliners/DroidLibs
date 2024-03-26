# Capturable

<img title="" src="assets/android-only.png" alt="" width="150">

`Capturable` is a composable that allows you to (capture / take snapshot of / take screenshot of) any composable.

<img src="assets/CapturableSample.gif" title="" alt="" width="270">

## Step 1 : Define state

```kotlin
val captureState = rememberCaptureState() // Returns MutableState<CaptureState?>
```

## Step 2 : Composable

Enclose the composable to be captured inside the `Capturable` composable :

```kotlin
@Composable
fun Capturable(
    state: MutableState<CaptureState?>,
    content: @Composable () -> Unit
)
```

Example :

```kotlin
Capturable(state = captureState) {
    CountryCard(
        country = Country("IN", "Bharat", "Asia")
    )
}
```

Everything inside the content composable will be captured.

## Step 3 : Capture

To capture the passed composable as `Bitmap`, invoke the `captureState.capture()` function :

```kotlin
fun MutableState<CaptureState?>.capture(
    callback: (Bitmap) -> Unit // Use the bitmap inside this lambda
)
```

Example :

```kotlin
Button(
    onClick = {
        captureState.capture { bitmap ->
            saveAndShareImage(bitmap)
        }
    }
) {
    Text(text = "Capture & Share")
}
```

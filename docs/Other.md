# Other

## FilledIconButtonSmall

<img src="assets/FilledIconButtonSmall.png" title="" alt="" width="595">

`FilledIconButtonSmall` is a small variant (size = `24.dp`) of Material UI's `FilledIconButton` (size = `40.dp`).

```kotlin
@Composable
fun FilledIconButtonSmall(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    colors: IconButtonColors = IconButtonDefaults.filledIconButtonColors(),
    contentDescription: String? = null,
    onClick: () -> Unit = { },
    enabled: Boolean = true
)
```

Example :

```kotlin
FilledIconButtonSmall(
    icon = Icons.Default.Call,
    onClick = { call() }
)
```

---

## Center & CenterText

`Center` composable is a `Column` with maximum size, both alignments as `Center` and default a padding of `16.dp`.

```kotlin
@Composable
fun Center(
    modifier: Modifier = Modifier.fillMaxSize().padding(16.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        content()
    }
}
```

Example :

```kotlin
Center {
    Button(
        onClick = { navController.navigateUp() }
    ) {
        Text(text = "Go back")
    }
}
```

<img title="" src="assets/CenterSample.png" alt="" width="235">

If you just want to show text inside `Center`, we have `CenterText` for that :

```kotlin
@Composable
fun CenterText(
    modifier: Modifier = Modifier.fillMaxSize().padding(16.dp),
    text: String,
    style: TextStyle = MaterialTheme.typography.titleMedium
)
```

---

## noRippleClickable

![](assets/NoRippleClickableSample.gif)

`Modifier#clickable()` function by default adds a ripple animation when the element is clicked. If you want to disable this behavior, use the `noRippleClickable()` function :

```kotlin
fun Modifier.noRippleClickable(
    onClick: () -> Unit
): Modifier
```

Example :

```kotlin
Text(
    modifier = Modifier
        .noRippleClickable {
            showToast("NO Ripple click")
        },
    text = "Without Ripple"
)
```

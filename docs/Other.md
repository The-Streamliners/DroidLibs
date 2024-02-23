# Other

## FilledIconButtonSmall

<img src="file:///Users/lavishswarnkar/Code/Projects/DroidLibs/docs/assets/FilledIconButtonSmall.png" title="" alt="" width="595">

`FilledIconButtonSmall` is a small variant (size = 24.dp) of Material UI's `FilledIconButton` (size = 40.dp).

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

## noRippleClickable

`Modifier#clickable()` function by default adds a ripple animation when the element is clicked. If you want to disable this behavior, use the `noRippleClickable()` function :

```kotlin
fun Modifier.noRippleClickable(
    onClick: () -> Unit
): Modifier
```

Example :

```kot

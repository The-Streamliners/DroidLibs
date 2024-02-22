# TitleBar

<img src="file://assets/TitleBar.png" title="" alt="" width="400">

`TitleBar` is a wrapper around `TopAppBar` and a short replacement for the following code which is often repeated all over the app :

```kotlin
Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
        TopAppBar(
            title = {
                Text(title)
            }
            // Custom Navigation Icon or Navigate Up
            // Actions
        )
    }
) { paddingValues ->
    // ... Content
}
```

The replacement for this code is the `TitleBarScaffold` composable :

```kotlin
@Composable
fun TitleBarScaffold(
    title: String,
    navigationIcon: (@Composable () -> Unit)? = null,
    navigateUp: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
)
```

You can define the following :

- `title` to be displayed on the `TopAppBar`

- `navigationIcon` composable to be displayed to the left of title
  
  <img src="file://assets/TitleBarWithNavIcon.png" title="" alt="" width="400">

- `navigateUp` lambda, when passed, displays Back `navigationIcon`, on click of which this lambda is executed
  
  <img src="file://assets/TitleBarWithUpNav.png" title="" alt="" width="400">

- `actions` composable to be displayed at the end of the `TopAppBar`
  
  <img src="file://assets/TitleBarWithActions.png" title="" alt="" width="400">

Example :

```kotlin
TitleBarScaffold(
    title = "DroidLibs Sample",
    navigateUp = { navController.navigateUp() }
) { paddingValues ->
    // ... Content
}
```

If you want to define your own `Scaffold`, then use the `TitleBar` composable :

```kotlin
@Composable
fun TitleBar(
    title: String,
    navigationIcon: (@Composable () -> Unit)? = null,
    navigateUp: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
)
```

Example :

```kotlin
Scaffold(
    topBar = {
        TitleBar(
            title = "Profile"
        )
    }
) { paddingValues ->
    // ... Content
}
```

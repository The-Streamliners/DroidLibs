# Labelled CheckBox

The CheckBox provided by Material library is just a CheckBox, without any label. But a CheckBox is often used along with a label. For this purpose, we provide LabelledCheckBox. It is a wrapper around CheckBox, with additional features :

- Displays label

- Label as well as CheckBox are clickable with ripple effect

![](assets/LabelledCheckBoxDemo.gif)

## Usage

There are two ways to use LabelledCheckBox :

### 1. Pass the MutableState directly

```kotlin
@Composable
fun LabelledCheckBox(
    modifier: Modifier = Modifier,
    state: MutableState<Boolean>,
    label: String,
    labelStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    enabled: Boolean = true,
    onCheckChanged: (Boolean) -> Unit = {}
)
```

Example :

```kotlin
val checked = remember { mutableStateOf(false) }

// State passed directly
LabelledCheckBox(
    state = checked,
    label = "Gift wrap"
)
```

Note that because state is passed directly, it will automatically be updated when clicked. Still, if you need a callback for when check is changed, use the onCheckChanged lambda parameter. There is no need to update the state in the lambda passed.

### 2. Control the MutableState yourself

Instead of passing in the MutableState, you can pass the state and handle the event yourself :

```kotlin
@Composable
fun LabelledCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onToggle: (Boolean) -> Unit,
    label: String,
    enabled: Boolean = true,
    labelStyle: TextStyle = MaterialTheme.typography.bodyLarge
) 
```

Example :

```kotlin
var checked by remember { mutableStateOf(false) }

LabelledCheckBox(
    checked = checked,
    onToggle = { checked = it },
    label = "Gift wrap"
)
```

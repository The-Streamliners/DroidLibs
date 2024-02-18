# TextInputDialog

TextInputDialog is well, a dialog built using the TextInputLayout (TIL) for the purpose of simple text input. 

https://github.com/The-Streamliners/DroidLibs/assets/24524454/e69729af-b6d3-44af-b584-4856d4b2b315

You can use it in 3 simple steps :

### Step 1 - Define state

Either inside a Composable :

```kotlin
val textInputDialogState = rememberTextInputDialogState()
```

or inside a ViewModel :

```kotlin
val textInputDialogState = textInputDialogState()
```

### Step 2 - Invoke Composable

```kotlin
TextInputDialog(state = textInputDialogState)
```

### Step 3 - Show dialog

You can show the dialog whenever you want just by mutating the state defined in Step#1. `TextInputDialogState` is a sealed class with two possible values - `Hidden` (initialization value) & `Visible`. Update the state to `Visible` to display the dialog :

```kotlin
class Visible(
    val title: String,
    val input: MutableState<TextInputState>,
    val submitButtonLabel: String = "Submit",
    val submit: (String) -> Unit
): TextInputDialogState()
```

For example, below statement mutates the state in order to display the dialog for inputting birth year of the user.

```kotlin
textInputDialogState.value = TextInputDialogState.Visible(
    title = "Enter your birth year",
    input = mutableStateOf(
        TextInputState(
            label = "Year",
            inputConfig = InputConfig.fixedLengthNumber(4)
        )
    ),
    submit = { input ->
        val birthYear = input.toInt() // No need to worry about error; already handled by TIL
        val age = Calendar.getInstance().get(YEAR) - birthYear
        showMessageDialog("Age", "Your age is $age years.")
    }
)
```

Worth Noting :

- Because TextInputDialog uses TextInputLayout (TIL), all of TIL's validations are as-is applicable here too.

- If your use case requires showing different `TextInputDialog`s, you don't have to define multiple states. A single state is reusable across all triggers.

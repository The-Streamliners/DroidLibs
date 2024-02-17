# Some helper functions

## Log

Helper function to log from anywhere in the app :

```kotlin
fun log(
    message: String,
    tag: String? = null,
    isError: Boolean = false,
    buildType: String
)
```

- when `isError` is passed as `true`, log is printed on the error stream otherwise on the info stream.

- `buildType` is taken as input to decide whether log should be printed. Pass your app's `BuildConfig.BUILD_TYPE`, which if equals `"debug"` then only log is printed.

> If `BuildConfig` class is not resolved, set `android.buildFeatures.buildConfig` to `true` in your app's Gradle file and rebuild the project.

## CoroutineContext & ExceptionHandler

Helper function to get a `CoroutineContext` in built with exception handling :

```kotlin
fun defaultContext(
    tag: String? = null,
    onExceptionOccurred: (Throwable) -> Unit = {},
    buildType: String
): CoroutineContext
```

- uses `Dispatchers.IO` by default

When exception occurs,

- exception message & stack trace is logged (if `"debug"` buildType)

- `onExceptionOccurred` lambda is invoked

Or if you prefer to get only an exception handler, call this function :

```kotlin
fun defaultExceptionHandler(
    tag: String? = null,
    onExceptionOccurred: (Throwable) -> Unit = {},
    buildType: String
): CoroutineExceptionHandler
```

## Execute handling error

Using the `CoroutineContext` & `ExceptionHandler` returned by the above described unctions, if you want to execute a `suspend` lambda, use this function :

```kotlin
fun defaultExecuteHandlingError(
    tag: String? = null,
    onExceptionOccurred: (Throwable) -> Unit = {},
    lambda: suspend CoroutineScope.() -> Unit,
    buildType: String
)
```

> `defaultContext()`, `defaultExceptionHandler()`, `defaultExecuteHandlingError() `functions might be useful in different Android components other than `Activity` & `ViewModel` (because `BaseActivity` & `BaseViewModel` helper functions can be accessed there) like `Service`, `BroadcastReceiver` etc.

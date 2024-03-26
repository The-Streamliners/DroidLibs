pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        id("com.android.library") version "8.2.0-rc02"
        id("org.jetbrains.kotlin.android") version "1.9.0"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "DroidLibs"
include(":app")
include(":compose")
include(":base")
include(":pickers")
include(":utils")
include(":helpers")
include(":compose-android")

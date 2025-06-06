// In your project-level build.gradle.kts (not app/build.gradle.kts)

plugins {
    // Correct way to define the Android Application plugin
    id("com.android.application") version "8.2.0" apply false // <-- REPLACE "8.2.0" with your actual AGP version (e.g., 8.3.0, 8.4.0)

    id("org.jetbrains.kotlin.android") version "1.9.0" apply false // Ensure this matches your Kotlin version
    id("androidx.navigation.safeargs") version "2.7.7" apply false // From previous fixes
    id("com.google.dagger.hilt.android") version "2.51.1" apply false // From previous fixes
}
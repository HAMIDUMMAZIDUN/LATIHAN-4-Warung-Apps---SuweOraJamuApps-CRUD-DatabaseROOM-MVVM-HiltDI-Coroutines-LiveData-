// In your settings.gradle.kts

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // ⬅️ **IMPORTANT for finding plugins like Hilt**
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SuweOraJamuHamid" // Or your actual project name
include(":app") // Or your actual app module name
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Jedi Archive"

// Main app
include(":app")

// Feature modules
include(":feature:search")
include(":feature:detail")
include(":feature:settings")

// Core modules
include(":core:ui")
include(":core:designsystem")
include(":core:domain")
include(":core:data")
include(":core:network")
include(":core:datastore")
include(":core:navigation")

// Agent system modules
include(":core:agents")
include(":agents:architect")
include(":agents:engineer")
include(":agents:uiux")
include(":agents:api")
include(":agents:qa")

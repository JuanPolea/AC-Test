pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        gradlePluginPortal()
        google()
        mavenLocal()
        mavenCentral()
        maven("https://jitpack.io")
    }

    versionCatalogs {
        create("libs") {
            from(files("gradle/libraries.versions.toml"))
        }
    }
}

rootProject.name = "AC-Test"
include(":app")
include(":presentation:ui")

include(":domain:model")
include(":domain:repository")

include(":framework")

include(":data:api")
include(":data:remote")
include(":data:cache")
include(":data:repository")
include(":data:paging")
include(":test")
include(":domain:usecase")

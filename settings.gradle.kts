rootProject.name = "AC-Test"
include(":presentation")
include(":usecase")
include(":domain")
include(":domain:model")
include(":framework:room")
include(":data:repository")
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
    }
}
rootProject.name = "AC-Test"
include(":app")
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
include(":data:open")
include(":data:repository:model")
include(":data:di")
include(":usecase:di")
include(":usecase:lib")

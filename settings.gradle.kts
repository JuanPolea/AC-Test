pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }

}
rootProject.name = "AC-Test"
include(":app")
include(":usecase")
include(":usecase:di")
include(":data")
include(":data:di")
include(":data:open")
include(":data:repository")
include(":data:repository:model")
include(":data:repository:open")
include(":data:repository:implementation")
include(":domain")
include(":domain:model")
include(":framework")
include(":framework:room")


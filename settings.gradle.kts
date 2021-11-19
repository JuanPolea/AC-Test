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
include(":usecase:open")
include(":usecase:implementation")
include(":usecase:di")
include(":data")
include(":data:open")
include(":data:repository")
include(":data:repository:model")
include(":data:repository:open")
include(":data:repository:implementation")
include(":data:repository:di")
include(":data:di")
include(":domain")
include(":domain:model")
include(":framework")
include(":framework:room")



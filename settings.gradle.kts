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
include(":usecase:lib")
include(":data")
include(":data:di")
include(":data:repository")
include(":data:repository:model")
include(":data:open")
include(":domain")
include(":domain:model")
include(":framework")
include(":framework:room")

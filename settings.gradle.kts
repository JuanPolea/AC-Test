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
include(":domain:usecase")
include(":domain:usecase:open")
include(":domain:usecase:implementation")
include(":domain:usecase:di")
include(":data")
include(":data:open")
include(":data:di")
include(":domain")
include(":domain:model")
include(":framework")
include(":framework:room")


include(":domain:repository")
include(":domain:repository:open")
include(":domain:repository:implementation")
include(":domain:repository:di")
include(":data:remote")
include(":data:remote:model")

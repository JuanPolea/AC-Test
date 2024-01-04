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
            library("material", "com.google.android.material:material:1.9.0")
            library("android-ktx", "androidx.core:core-ktx:1.10.1")
            library("android-lifecycle-runtime", "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
            bundle("android", listOf("android-ktx", "android-lifecycle-runtime"))

            version("coil", "2.4.0")
            version("navigation", "2.6.0")
            library(
                "compose-navigation",
                "androidx.navigation",
                "navigation-compose"
            ).versionRef("navigation")
            library("compose-coil", "io.coil-kt", "coil-compose").versionRef("coil")
            library("compose-activity", "androidx.activity:activity-compose:1.7.2")
            bundle("compose", listOf("compose-navigation", "compose-coil", "compose-activity"))
            version("androidxComposeBom", "2023.10.01")
            library(
                "androidx-compose-bom",
                "androidx.compose",
                "compose-bom"
            ).versionRef("androidxComposeBom")

            library("hilt-android", "com.google.dagger:hilt-android:2.46.1")
            library("hilt-compiler", "com.google.dagger:hilt-compiler:2.46.1")
            library("hilt-compose", "androidx.hilt:hilt-navigation-compose:1.0.0")
            bundle("hilt", listOf("hilt-android", "hilt-compose"))
            bundle("hiltkapt", listOf("hilt-compiler"))

            library("kotlin-coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            library(
                "kotlin-serialization",
                "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1"
            )

            version("room", "2.5.0")
            library("room-runtime", "androidx.room", "room-runtime").versionRef("room")
            library("room-ktx", "androidx.room", "room-ktx").versionRef("room")
            library("room-compiler", "androidx.room", "room-compiler").versionRef("room")
            bundle("room", listOf("room-runtime", "room-ktx"))

            library("google-gson", "com.google.code.gson:gson:2.9.0")

            library("retrofit2", "com.squareup.retrofit2:retrofit:2.9.0")
            library(
                "retrofit2-adapter",
                "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
            )
            library("retrofit2-converter-gson", "com.squareup.retrofit2:converter-gson:2.9.0")
            library("okhttp3-interceptor", "com.squareup.okhttp3:logging-interceptor:4.9.0")
            bundle(
                "retrofit2",
                listOf(
                    "retrofit2",
                    "retrofit2-adapter",
                    "retrofit2-converter-gson",
                    "okhttp3-interceptor"
                )
            )

            library("arrow-core", "io.arrow-kt:arrow-core:0.11.0")
            library("arrow-syntax", "io.arrow-kt:arrow-syntax:0.11.0")
            library("arrow-kapt", "io.arrow-kt:arrow-meta:0.11.0")
            bundle("arrow", listOf("arrow-core", "arrow-syntax"))

            library("timber", "com.jakewharton.timber:timber:5.0.1")

            version("paging", "3.2.1")
            library("paging-common", "androidx.paging", "paging-common-ktx").versionRef("paging")
            library("paging-runtime", "androidx.paging", "paging-runtime-ktx").versionRef("paging")
            library("paging-room", "androidx.room:room-paging:2.6.1")
            library("paging-compose", "androidx.paging", "paging-compose").version("3.3.0-alpha02")
            bundle("paging", listOf("paging-common", "paging-runtime"))

            library(
                "accompanist-swiperefresh",
                "com.google.accompanist",
                "accompanist-swiperefresh"
            ).version("0.28.0")

            library(
                "firebase-crashlitycs",
                "com.google.firebase",
                "firebase-crashlytics-ktx"
            ).version("18.3.3")

            version("compose-test", "1.3.1")
            library("test-junit", "junit:junit:4.13.2")
            library("android-test-junit", "androidx.test.ext:junit:1.1.3")
            library(
                "test-compose-ui",
                "androidx.compose.ui",
                "ui-test-junit4"
            ).versionRef("compose-test")
            library(
                "test-compose-tooling",
                "androidx.compose.ui",
                "ui-tooling"
            ).versionRef("compose-test")
            library(
                "test-compose-manifest",
                "androidx.compose.ui",
                "ui-test-manifest"
            ).versionRef("compose-test")
            library("mockk-main", "io.mockk:mockk:1.13.3")
            library("test-kotlin-coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
            library("test-kotlin", "org.jetbrains.kotlin:kotlin-test:1.8.0")
            library("test-junit-ktx", "androidx.test.ext:junit-ktx:1.1.5")
            library("test-mockk", "io.mockk:mockk:1.13.3")
            bundle(
                "compose-instrumentationTest",
                listOf("test-compose-ui", "test-compose-tooling", "test-compose-manifest")
            )
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
include(":data:di")
include(":data:remote")
include(":data:cache")
include(":data:repository")
include(":data:paging")
include(":test")
include(":domain:usecase")

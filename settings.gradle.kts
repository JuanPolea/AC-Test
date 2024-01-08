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
            version("androidx-lifecycle", "2.6.2")
            //Android
            library("androidx-ktx", "androidx.core:core-ktx:1.10.1")
            library("androidx-activity-ktx", "androidx.activity:activity-ktx:1.8.2")
            library("androidx-fragment-ktx", "androidx.fragment:fragment-ktx:1.6.2")
            library(
                "androidx-lifecycle-viewmodel-ktx",
                "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2"
            )
            library(
                "androidx-lifecycle-viewmodel-compose",
                "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2"
            )
            library(
                "androidx-lifecycle-runtime",
                "androidx.lifecycle:lifecycle-runtime-compose:2.6.2"
            )
            library(
                "androidx-lifecycle-runtime-ktx",
                "androidx.lifecycle:lifecycle-runtime-ktx:2.6.2"
            )
            bundle(
                "androidx",
                listOf(
                    "androidx-ktx",
                    "androidx-lifecycle-viewmodel-ktx",
                    "androidx-lifecycle-viewmodel-compose",
                    "androidx-lifecycle-runtime",
                    "androidx-lifecycle-runtime-ktx",
                    "androidx-activity-ktx",
                    "androidx-fragment-ktx"
                )
            )

            version("paging", "3.2.1")
            library("paging-common", "androidx.paging", "paging-common-ktx").versionRef("paging")
            library("paging-runtime", "androidx.paging", "paging-runtime-ktx").versionRef("paging")
            library("paging-room", "androidx.room:room-paging:2.6.1")
            library("paging-compose", "androidx.paging", "paging-compose").version("3.3.0-alpha02")
            bundle("paging", listOf("paging-common", "paging-runtime", "paging-room"))
            //Kotlin
            version("kotlin-bom", "1.9.21")
            library("kotlin-bom", "org.jetbrains.kotlin", "kotlin-bom").versionRef("kotlin-bom")
            library("kotlin-coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            library(
                "kotlin-serialization",
                "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1"
            )
            library(
                "kotlin-stdlib",
                "org.jetbrains.kotlin",
                "kotlin-stdlib-common"
            ).versionRef("kotlin-bom")
            bundle(
                "kotlin",
                listOf(
                    "kotlin-coroutines",
                    "kotlin-stdlib",
                )
            )


            //Hilt
            version("hilt-android", "2.50")
            library("hilt-android", "com.google.dagger", "hilt-android").versionRef("hilt-android")
            library(
                "hilt-compiler",
                "com.google.dagger",
                "hilt-compiler"
            ).versionRef("hilt-android")
            library("hilt-compose", "androidx.hilt:hilt-navigation-compose:1.0.0")
            bundle("hilt", listOf("hilt-android", "hilt-compose"))
            bundle("hiltkapt", listOf("hilt-compiler"))

            //Google
            library("material", "com.google.android.material:material:1.9.0")
            library("google-gson", "com.google.code.gson:gson:2.9.0")

            //Compose
            version("androidxComposeBom", "2023.10.01")
            version("compose-navigation", "2.7.1")
            version("coil", "2.5.0")

            version("activity-compose", "1.8.2")

            library(
                "androidx-compose-bom",
                "androidx.compose",
                "compose-bom"
            ).versionRef("androidxComposeBom")

            library(
                "androidx-compose-foundation",
                "androidx.compose.foundation",
                "foundation"
            ).versionRef("androidxComposeBom")
            library(
                "androidx-compose-ui",
                "androidx.compose.ui",
                "ui"
            ).versionRef("androidxComposeBom")
            library(
                "androidx-compose-material",
                "androidx.material",
                "material"
            ).versionRef("androidxComposeBom")
            library(
                "androidx.compose.material3",
                "androidx.compose.material3",
                "material3"
            ).versionRef("androidxComposeBom")
            library(
                "androidx-compose-ui-tooling-preview",
                "androidx.ui",
                "ui-tooling-preview"
            ).versionRef("androidxComposeBom")
            library(
                "androidx-compose-ui-test-junit4",
                "androidx.ui",
                "ui-test-junit4"
            ).versionRef("androidxComposeBom")
            library(
                "androidx-compose-ui-tooling-test-debug",
                "androidx.ui",
                "ui-tooling"
            ).versionRef("androidxComposeBom")
            library(
                "androidx-compose-ui-graphics",
                "androidx.ui",
                "ui-graphics"
            ).versionRef("androidxComposeBom")
            library(
                "androidx-compose-activity",
                "androidx.activity",
                "activity-compose"
            ).versionRef("activity-compose")
            library("coil-compose", "io.coil-kt", "coil-compose").versionRef("coil")
            library(
                "androidx-compose-navigation",
                "androidx.navigation",
                "navigation-compose"
            ).versionRef("compose-navigation")
            bundle(
                "compose-bom", listOf(
                    "androidx-compose-foundation",
                    "androidx-compose-ui",
                    "androidx-compose-material",
                    "androidx-compose-material3",
                    "androidx-compose-ui-tooling-preview",
                    "androidx-compose-ui-test-junit4",
                    "androidx-compose-ui-tooling-test-debug",
                    "androidx-compose-ui-graphics",
                    "coil-compose",
                    "androidx-compose-navigation",
                    "androidx-compose-activity"
                )
            )

            //ROOM
            version("room", "2.6.1")
            library("room-runtime", "androidx.room", "room-runtime").versionRef("room")
            library("room-ktx", "androidx.room", "room-ktx").versionRef("room")
            library("room-compiler", "androidx.room", "room-compiler").versionRef("room")
            bundle("room", listOf("room-runtime", "room-ktx"))

            //Retrofit
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

            //Arrow
            library("arrow-core", "io.arrow-kt:arrow-core:0.11.0")
            library("arrow-syntax", "io.arrow-kt:arrow-syntax:0.11.0")
            library("arrow-kapt", "io.arrow-kt:arrow-meta:0.11.0")
            bundle("arrow", listOf("arrow-core", "arrow-syntax"))

            library("timber", "com.jakewharton.timber:timber:5.0.1")

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
include(":data:remote")
include(":data:cache")
include(":data:repository")
include(":data:paging")
include(":test")
include(":domain:usecase")

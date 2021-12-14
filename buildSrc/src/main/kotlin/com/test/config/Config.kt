object BuildConfig {
    const val baseUrl = "BASE_URL"
    const val baseUrlRelease = "\"https://rickandmortyapi.com/api/\""
    const val baseUrlDebug = "\"https://rickandmortyapi.com/api/\""
}

object DefaultConfig {
    const val applicationId = "com.jfmr.ac.test"
    const val applicationName = "AC-JFMR"
    private const val versionMajor = 0
    private const val versionMinor = 1
    private const val versionPatch = 0
    const val versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"
    const val projectMinSdkVersion = 23
    const val projectCompileSdkVersion = 31
    const val projectTargetSdkVersion = 30
    const val buildToolsVersion = "30.0.3"
}

object SourceDirs {
    const val main = "main"
    const val test = "test"
    const val androidTest = "androidTest"
    const val mainKotlin = "src/main/kotlin"
    const val testKotlin = "src/test/kotlin"
    const val androidTestKotlin = "src/androidTest/kotlin"
    const val mainJava = "src/main/java"
    const val testJava = "src/test/java"
    const val androidTestJava = "src/androidTest/java"

}

object Gradle {
    object Versions {
        const val gradleVersion = "7.0.0-rc01"
        const val plugin = "4.2.0"
    }

    const val androidBuildGradle = "com.android.tools.build:gradle:${Versions.gradleVersion}"

}

object Androidx {
    object Versions {
        const val appCompat = "1.2.0"
        const val safeArgs = "2.3.5"
        const val coreKtx = "1.7.0"
        const val recycler = "1.0.0"
        const val androidxLifecycle = "2.2.0"
        const val androidxLifecycleRuntime = "2.4.0"
        const val nav_version = "2.3.5"
        const val junit4 = "4.12"
        const val testRunner = "1.1.0-alpha4"
        const val espresso = "3.1.0-alpha4"
        const val androidxTestArch = "2.1.0"
        const val room = "2.2.6"
        const val annotation = "1.1.0"
        const val testCore = "1.3.0"
        const val instrumentationTest = "1.1.2"
        const val testRules = "1.3.0"
        const val activity = "1.2.3"
        const val compose = "1.1.0-beta03"
        const val activityCompose = "1.4.0"
        const val navigation = "2.4.0-beta02"
        const val coil = "1.4.0"
    }

    object Plugins {
        const val androidApplication = "com.android.application"
        const val androidLibrary = "com.android.library"
        const val android = "android"
        const val androidExtensions = "android.extensions"
        const val safeArgs = "androidx.navigation.safeargs.kotlin"
    }

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    val dirToLibs = mapOf("dir" to "libs", "include" to listOf("*.jar"))
    const val androidxCoreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val lifeCycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.androidxLifecycle}"
    const val lifeCycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidxLifecycleRuntime}"
    const val navigationPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.safeArgs}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val annotations = "androidx.annotation:annotation:${Versions.annotation}"

    //Testing
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.androidxTestArch}"
    const val test = "androidx.test:core:${Versions.testCore}"
    const val instrumentationTest = "androidx.test.ext:junit:${Versions.instrumentationTest}"
    const val testRules = "androidx.test:rules:${Versions.testRules}"

    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    const val composeUI = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeTooling = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val composeActivity = "androidx.activity:activity-compose:${Versions.activity}"
    const val composeJUnit = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.navigation}"
    const val composeCompiler = "androidx.compose.compiler:compiler:${Versions.compose}"
    const val composeUiTest = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    const val composeUiManifestTest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
    const val composeFundation = "androidx.compose.foundation:foundation:${Versions.compose}"
    const val composeUiUtil = "androidx.compose.ui:ui-util:${Versions.compose}"
    const val composeCoil = "io.coil-kt:coil-compose:${Versions.coil}"
}

object Kotlin {
    object Versions {
        const val core = "1.5.31"
        const val anotations = "1.1.0"
        const val coroutines = "1.5.2"
    }

    object Plugins {
        const val kotlin = "kotlin"
        const val kotlinAndroid = "kotlin-android"
        const val kotlinKapt = "kotlin-kapt"
        const val kapt = "kapt"
        const val kotlinParcelize = "kotlin-parcelize"
    }

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.core}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.core}"
    const val test = "org.jetbrains.kotlin:kotlin-test:${Versions.core}"
    const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.core}"
}

object Google {
    object Versions {
        const val materialComponents = "1.2.1"
        const val gsonVersion = "2.8.6"
        const val jvm = "0.2.0"
    }

    const val material = "com.google.android.material:material:${Versions.materialComponents}"
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"
}


//Network
object SquareApp {
    object Versions {
        const val retrofitVersion = "2.9.0"
        const val okHttp = "4.9.0"
        const val gson = "2.8.6"
        const val retrofitMock = "2.3.0"
        const val coroutinesAdapter = "0.9.2"
        const val retrofitMockWebServer = "4.2.2"
        const val timberVersion = "4.7.1"
        const val threeTenAbp = "1.2.1"
    }

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    const val loginInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val retrofitCoroutines = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.coroutinesAdapter}"
    const val retrofitMock = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofitMock}"
    const val retrofitMockWerbServer = "com.squareup.okhttp3:mockwebserver:${Versions.retrofitMockWebServer}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"
    const val threeTeen = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTenAbp}"
}

object Detekt {
    object Versions {
        const val core = "1.16.0-RC1"
    }

    object Plugins {
        const val detektIO = "io.gitlab.arturbosch.detekt"
    }

    const val gradlePlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.core}"
}

object Hilt {
    object Versions {
        const val gradle = "2.40"
        const val hiltAndroid = "2.40"
        const val hiltViewModel = "1.0.0-alpha03"
        const val hiltNavigation = "1.0.0-alpha03"
        const val hiltAndroidCompiler = "2.40"
        const val hiltCompiler = "1.0.0"
    }

    object Plugins {
        const val hilt = "dagger.hilt.android.plugin"
    }

    const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.gradle}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltAndroid}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroidCompiler}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}"
    const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigation}"
    const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltViewModel}"
}

object Accompanist {
    object Versions {
        const val core = "0.20.0"
    }

    const val permissions = "com.google.accompanist:accompanist-permissions:${Versions.core}"
    const val flow = "com.google.accompanist:accompanist-flowlayout:${Versions.core}"
}

object JVMTarget {
    object Versions {
        const val core = "1.8"
    }
}

object Arrow {
    object Versions {
        const val core = "0.11.0"
    }

    const val core = "io.arrow-kt:arrow-core:${Versions.core}"
    const val sintax = "io.arrow-kt:arrow-syntax:${Versions.core}"
    const val meta = "io.arrow-kt:arrow-meta:${Versions.core}"
}
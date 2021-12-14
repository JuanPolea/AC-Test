plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin(Kotlin.Plugins.kapt)
    id(Hilt.Plugins.hilt)
}
android {
    compileSdk = 31

    defaultConfig {
        applicationId = DefaultConfig.applicationId
        minSdk = DefaultConfig.projectMinSdkVersion
        targetSdk = DefaultConfig.projectTargetSdkVersion
        versionCode = DefaultConfig.versionCode
        versionName = DefaultConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Androidx.Versions.compose
    }
    buildFeatures {
        compose = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
    buildTypes {
        debug { }
        release { }
    }
    hilt {
        enableExperimentalClasspathAggregation = true
    }
}

dependencies {
    implementation(project(mapOf("path" to ":presentation:ui")))
    implementation(Kotlin.coroutinesCore)
    implementation(Androidx.androidxCoreKtx)
    implementation(Androidx.appCompat)
    implementation(Androidx.composeUI)
    implementation(Androidx.composeCompiler)
    implementation(Androidx.composeMaterial)
    implementation(Androidx.composeCoil)
    debugImplementation(Androidx.composeUiTooling)
    implementation(Google.material)
    implementation(Androidx.composeUiToolingPreview)
    implementation(Androidx.composeActivity)
    implementation(Androidx.lifeCycleRuntime)
    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)
    kapt(Hilt.hiltAndroidCompiler)
    implementation(Hilt.hiltViewModel)
    implementation(Hilt.hiltNavigation)
    implementation(SquareApp.timber)
    implementation(Arrow.core)
    kapt(Arrow.meta)
    implementation(Arrow.sintax)
}
kapt {
    correctErrorTypes = true
}
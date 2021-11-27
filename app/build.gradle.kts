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
    kotlinOptions {
        jvmTarget = JVMTarget.Versions.core
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
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(project(mapOf("path" to ":usecase:open")))
    implementation(project(mapOf("path" to ":usecase:di")))
    implementation(project(mapOf("path" to ":usecase:implementation")))
    implementation(project(mapOf("path" to ":data:repository")))
    implementation(project(mapOf("path" to ":data:di")))
    implementation(project(mapOf("path" to ":data:open")))
    implementation(project(mapOf("path" to ":domain:model")))
    implementation(Kotlin.coroutinesCore)
    implementation(Androidx.androidxCoreKtx)
    implementation(Androidx.appCompat)
    implementation(Androidx.composeUI)
    implementation(Androidx.composeCompiler)
    implementation(Androidx.composeMaterial)
    implementation(project(mapOf("path" to ":data:repository:di")))
    implementation(project(mapOf("path" to ":data:repository:open")))
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
    implementation(JakeWharton.timber)
}
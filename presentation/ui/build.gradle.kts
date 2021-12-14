plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
    id(Hilt.Plugins.hilt)
}
android {
    compileSdk = DefaultConfig.projectCompileSdkVersion

    defaultConfig {
        minSdk = DefaultConfig.projectMinSdkVersion
        targetSdk = DefaultConfig.projectTargetSdkVersion
        testInstrumentationRunner = Androidx.testInstrumentationRunner
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Androidx.Versions.compose
    }
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = JVMTarget.Versions.core
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
    implementation(project(mapOf("path" to ":domain:usecase:di")))
    implementation(project(mapOf("path" to ":domain:usecase:open")))
    implementation(project(mapOf("path" to ":domain:model")))
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
    implementation(Androidx.composeNavigation)
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
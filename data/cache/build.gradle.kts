plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
    id(Hilt.Plugins.hilt)
    kotlin("plugin.serialization") version "1.5.10"
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
    implementation(project(":domain:model"))
    implementation(project(":data:cache:model"))
    implementation(project(":data:open"))
    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)
    kapt(Hilt.hiltAndroidCompiler)

    implementation(Androidx.roomRuntime)
    implementation(Androidx.roomKtx)
    kapt(Androidx.roomCompiler)

}
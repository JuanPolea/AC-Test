plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
    id(Hilt.Plugins.hilt)
    kotlin("plugin.serialization") version "1.5.10"
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"


    defaultConfig {
        minSdk = 23
        targetSdk = 30
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        debug {}
        release { }
    }


    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

implementation(project(":domain:model"))
    implementation(project(":data:repository"))
    implementation(project(":data:open"))
    implementation(Hilt.hiltAndroid)
    implementation(Hilt.hiltCompiler)
    implementation(Hilt.hiltAndroidCompiler)
    implementation(SquareApp.retrofit)
    implementation(SquareApp.retrofitCoroutines)
    implementation(SquareApp.retrofitGson)
    implementation(SquareApp.loginInterceptor)
    implementation(project(mapOf("path" to ":data:repository:implementation")))
    implementation(project(mapOf("path" to ":data:repository:open")))
}

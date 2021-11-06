plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
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


    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {
    implementation(kotlin("stdlib-jdk7"))
    implementation(project(":domain:model"))
    implementation(project(":data:repository"))
    implementation(project(":data:open"))
}

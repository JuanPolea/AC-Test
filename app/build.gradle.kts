plugins {
    kotlin("android")
    alias(libs.plugins.android.application)
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("kapt")
}

android {
    namespace = "com.jfmr.ac.test.app"

    defaultConfig {
        applicationId = "com.jfmr.ac.test.app"
    }
}

dependencies {
    implementation(project(":presentation:ui"))
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)
}

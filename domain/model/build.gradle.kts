plugins {
    id("com.android.library")
    kotlin("plugin.serialization")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.jfmr.ac.test.domain.model"
}

dependencies {
    api(libs.bundles.arrow)
    kapt(libs.arrow.kapt)
    api(libs.bundles.room)
    implementation(libs.retrofit2.converter.gson)
    api(libs.kotlin.serialization)
}

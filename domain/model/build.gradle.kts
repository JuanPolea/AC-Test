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
    api(libs.kotlin.serialization)
    api(libs.ktor.serialization.gson)
}

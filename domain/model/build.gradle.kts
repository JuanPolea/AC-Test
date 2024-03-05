plugins {
    alias(libs.plugins.android.library)
    kotlin("plugin.serialization")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.jfmr.ac.test.domain.model"
}

dependencies {
    api(libs.bundles.room)
    api(libs.ktor.serialization.kotlinx.json)
    api(libs.google.gson)
}

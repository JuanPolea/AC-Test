plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.jfmr.ac.test.data.api"
}

dependencies {
    api(libs.bundles.retrofit2)
    api(libs.kotlin.serialization)
}

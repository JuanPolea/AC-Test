plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.jfmr.ac.test.data.api"
    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/api/\"")
    }
}

dependencies {
    api(libs.bundles.retrofit2)
    implementation(libs.kotlin.serialization)
    implementation(libs.bundles.hilt)
    kapt(libs.bundles.hiltkapt)
    implementation(libs.kotlin.serialization)
}

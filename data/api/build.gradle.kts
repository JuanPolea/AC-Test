plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
}

android {
    namespace = "com.jfmr.ac.test.data.api"
    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/api/\"")
    }
}

dependencies {
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.bundles.ktor)
    implementation(libs.squareup.logging.interceptor)
    implementation(libs.google.gson)
}

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
    implementation(libs.kotlin.serialization)
    implementation(libs.bundles.hilt)
    kapt(libs.bundles.hiltkapt)
    implementation(libs.bundles.ktor)
    implementation(libs.ktor.serialization.gson)
    implementation(libs.ktor.client.serialization.kotlinx)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

}

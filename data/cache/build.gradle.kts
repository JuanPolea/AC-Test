plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.jfmr.ac.test.data.cache"
    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {
    api(project(":domain:model"))
    implementation(project(":test"))
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.kotlinx.coroutines.core)
    api(libs.bundles.paging)
    implementation(libs.bundles.room)
    kapt(libs.androidx.room.compiler)
    implementation(libs.firebase.crashlitycs)
}

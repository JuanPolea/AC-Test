plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.jfmr.ac.test.data.cache"
}

dependencies {
    api(project(":domain:model"))
    implementation(project(":test"))
    implementation(libs.google.gson)
    implementation(libs.bundles.hilt)
    kapt(libs.bundles.hiltkapt)
    implementation(libs.kotlin.coroutines)
    api(libs.bundles.paging)
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)
    implementation(libs.firebase.crashlitycs)
}

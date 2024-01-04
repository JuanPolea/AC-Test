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

    kapt(libs.room.compiler)
    implementation(libs.google.gson)
    implementation(libs.bundles.hilt)
    kapt(libs.bundles.hiltkapt)
    api(libs.kotlin.coroutines)
    api(libs.paging.room)
    api(libs.paging.common)
    implementation(libs.firebase.crashlitycs)
}

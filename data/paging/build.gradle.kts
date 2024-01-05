plugins {
    kotlin("android")
    id("com.android.library")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.jfmr.ac.test.data.paging"
}

dependencies {
    api(project(":data:cache"))
    api(project(":data:remote"))
    implementation(project(":test"))
    implementation(libs.android.ktx)
    kapt(libs.room.compiler)
    api(libs.paging.room)
    implementation(libs.google.gson)
    implementation(libs.bundles.hilt)
    kapt(libs.bundles.hiltkapt)
    api(libs.kotlin.coroutines)
    api(libs.paging.common)
    implementation(libs.firebase.crashlitycs)
}

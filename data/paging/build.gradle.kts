plugins {
    kotlin("android")
    alias(libs.plugins.android.library)
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
    implementation(libs.androidx.core.ktx)
    kapt(libs.androidx.room.compiler)
    api(libs.androidx.room.paging)
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)
    api(libs.kotlinx.coroutines.core)
    api(libs.androidx.paging.common)
    implementation(libs.firebase.crashlitycs)
}

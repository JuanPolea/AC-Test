plugins {
    alias(libs.plugins.android.library)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.jfmr.ac.test.data.remote"
}

dependencies {
    api(project(":data:api"))
    api(project(":domain:model"))
    implementation(libs.androidx.core.ktx)
    testImplementation(project(":test"))
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.firebase.crashlitycs)
}

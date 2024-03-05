plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("kapt")
}

android {
    namespace = "com.jfmr.ac.test.domain.usecase"
}

dependencies {
    api(project(":domain:model"))
    api(project(":data:repository"))
    api(project(":domain:repository"))
    api(project(":data:cache"))
    testImplementation(project(":test"))
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.firebase.crashlitycs)
}

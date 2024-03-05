plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("kapt")
}

android {
    namespace = "com.jfmr.ac.test.domain.repository"
}

dependencies {
    api(project(":data:cache"))
    api(project(":domain:model"))
    implementation(libs.androidx.core.ktx)
}

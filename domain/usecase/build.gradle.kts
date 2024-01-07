plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
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
    kapt(libs.bundles.hiltkapt)

    implementation(libs.bundles.arrow)
    kapt(libs.arrow.kapt)
    implementation(libs.firebase.crashlitycs)
}

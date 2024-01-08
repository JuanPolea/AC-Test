plugins {
    id("com.android.library")
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
    implementation(libs.androidx.ktx)

    testImplementation(project(":test"))

    implementation(libs.bundles.hilt)
    kapt(libs.bundles.hiltkapt)

    implementation(libs.bundles.arrow)
    kapt(libs.arrow.kapt)
    implementation(libs.firebase.crashlitycs)
}

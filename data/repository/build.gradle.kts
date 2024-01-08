plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("kapt")
}

android {
    namespace = "com.jfmr.ac.test.data.repository"
}

dependencies {
    api(project(":data:paging"))
    api(project(":domain:repository"))
    implementation(libs.androidx.ktx)
    testImplementation(project(":test"))

    implementation(libs.bundles.hilt)
    kapt(libs.bundles.hiltkapt)

    implementation(libs.bundles.arrow)
    kapt(libs.arrow.kapt)
    implementation(libs.firebase.crashlitycs)
}

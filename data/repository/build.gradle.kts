plugins {
    alias(libs.plugins.android.library)
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
    implementation(libs.androidx.core.ktx)
    testImplementation(project(":test"))

    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)

    
    
    implementation(libs.firebase.crashlitycs)
}

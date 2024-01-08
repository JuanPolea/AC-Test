plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("java-test-fixtures")
}

android {
    namespace = "com.jfmr.ac.test.tests"
    sourceSets {
        named("test") {
            resources.srcDirs("src/test/resources")
        }
    }
}

dependencies {
    implementation(project(":domain:model"))
    implementation(project(":data:api"))

    implementation(libs.paging.common)
    implementation(libs.androidx.ktx)
    kapt(libs.arrow.kapt)
    implementation(libs.bundles.arrow)

    implementation(libs.test.junit)
    implementation(libs.mockk.main)
    implementation(libs.google.gson)
    implementation(libs.kotlin.coroutines)
    implementation(libs.test.kotlin.coroutines)

    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.kotlin.coroutines)

    implementation(libs.bundles.retrofit2)
}

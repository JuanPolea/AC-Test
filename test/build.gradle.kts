plugins {
    alias(libs.plugins.android.library)
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
    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.core.ktx)
    implementation(libs.junit)
    implementation(libs.mockk)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.kotlinx.coroutines.core)
    implementation(libs.bundles.ktor)
    implementation(libs.squareup.logging.interceptor)
}

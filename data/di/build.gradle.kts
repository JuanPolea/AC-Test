plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.jfmr.ac.test.data.di"

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://rickandmortyapi.com/api/\"")
    }
}

dependencies {
    implementation(project(":data:remote"))
    implementation(project(":data:repository"))
    implementation(project(":data:cache"))
    implementation(project(":data:paging"))

    implementation(libs.bundles.hilt)
    kapt(libs.bundles.hiltkapt)

    kapt(libs.room.compiler)
}

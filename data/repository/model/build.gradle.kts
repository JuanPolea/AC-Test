plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
dependencies {
    implementation(project(":domain:model"))
    implementation(Kotlin.coroutinesCore)
    implementation(Google.gson)
    implementation(SquareApp.retrofit)
    implementation(SquareApp.retrofitGson)
    kapt(Hilt.hiltCompiler)
    implementation(Kotlin.coroutinesCore)
}

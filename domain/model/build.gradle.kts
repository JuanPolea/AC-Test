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
    kapt(Hilt.hiltCompiler)
    implementation(Arrow.core)
    kapt(Arrow.meta)
    implementation(Arrow.sintax)
}
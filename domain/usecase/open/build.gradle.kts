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
    implementation(project(":data:remote:model"))
    testImplementation("junit:junit:4.13.2")
    implementation(Kotlin.coroutinesCore)
    kapt(Hilt.hiltCompiler)
    implementation(Arrow.core)
    kapt(Arrow.meta)
    implementation(Arrow.sintax)
}
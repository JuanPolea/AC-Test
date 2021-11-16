plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}
dependencies {
    implementation(kotlin("stdlib-jdk7"))
    implementation(project(":domain:model"))
    implementation(Kotlin.coroutinesCore)
}
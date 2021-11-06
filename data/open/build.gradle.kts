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
    implementation(project(":domain"))
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    testImplementation("junit:junit:4.13.2")
    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")
    implementation(Google.gson)
    implementation(SquareApp.retrofit)
    implementation(SquareApp.retrofitGson)
    implementation(SquareApp.loginInterceptor)
}
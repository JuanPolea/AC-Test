plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.jfmr.ac.test.presentation.ui"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":domain:usecase"))
    implementation(project(":test"))
    implementation("androidx.metrics:metrics-performance:1.0.0-alpha04")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.9.21"))
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.compose.material:material")
    implementation(libs.paging.compose)
    api(libs.timber)
    api(libs.material)
    implementation(libs.bundles.android)
    implementation(libs.firebase.crashlitycs)
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")
    kapt("androidx.hilt:hilt-compiler:1.1.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.6")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-common:1.9.22")
    implementation(libs.accompanist.swiperefresh)
    androidTestImplementation(libs.bundles.compose.instrumentationTest)
    androidTestImplementation(libs.test.kotlin.coroutines)
    debugImplementation(libs.test.compose.manifest)
}

kapt {
    correctErrorTypes = true
}

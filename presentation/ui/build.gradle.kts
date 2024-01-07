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
    implementation(libs.bundles.android)
    implementation(platform(libs.kotlin.bom))
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material")
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    api(libs.material)
    api(libs.timber)
    implementation(libs.bundles.hilt)
    kapt(libs.bundles.hiltkapt)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.paging.compose)
    implementation(libs.firebase.crashlitycs)
    androidTestImplementation(libs.bundles.compose.instrumentationTest)
    androidTestImplementation(libs.test.kotlin.coroutines)
    debugImplementation(libs.test.compose.manifest)
}

kapt {
    correctErrorTypes = true
}

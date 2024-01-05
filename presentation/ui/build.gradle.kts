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

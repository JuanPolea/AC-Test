plugins {
    alias(libs.plugins.android.library)
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
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_20
        targetCompatibility = JavaVersion.VERSION_20
    }
    kotlinOptions {
        jvmTarget = "20"
    }
    packagingOptions {
        resources {
            excludes += "META-INF/*.version"
            excludes += "META-INF/**"
        }
    }
}

dependencies {
    implementation(project(":domain:usecase"))
    implementation(project(":test"))
    implementation(platform(libs.kotlin.bom))
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.paging.compose)
    implementation(libs.coil.kt.compose)
    implementation(libs.androidx.navigation.compose)
    api(libs.timber)
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.firebase.crashlitycs)
    androidTestImplementation(libs.bundles.androidx.compose.instrumentationTest)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.google.material)
}

kapt {
    correctErrorTypes = true
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        if (project.findProperty("composeCompilerReports") == "true") {
            freeCompilerArgs += listOf(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.buildDir.absolutePath}/compose_compiler"
            )
        }
        if (project.findProperty("composeCompilerMetrics") == "true") {
            freeCompilerArgs += listOf(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${project.buildDir.absolutePath}/compose_compiler"
            )
        }
    }
}

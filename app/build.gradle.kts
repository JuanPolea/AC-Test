plugins {
    id("com.android.application")
    id("kotlin-android")
}
android {
    compileSdk = 31

    defaultConfig {
        applicationId = DefaultConfig.applicationId
        minSdk = DefaultConfig.projectMinSdkVersion
        targetSdk = DefaultConfig.projectTargetSdkVersion
        versionCode = DefaultConfig.versionCode
        versionName = DefaultConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Androidx.Versions.compose
    }
    kotlinOptions {
        jvmTarget = JVMTarget.Versions.core
    }
    buildFeatures {
        compose = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":usecase"))
    implementation(project(":data:repository"))
    implementation(Androidx.androidxCoreKtx)
    implementation(Androidx.appCompat)
    implementation(Google.material)
    implementation(Androidx.composeUI)
    implementation(Androidx.composeCompiler)
    implementation(Androidx.composeMaterial)
    debugImplementation(Androidx.composeUiTooling)
    implementation(Androidx.composeUiToolingPreview)
    implementation(Androidx.composeActivity)
    implementation(Androidx.lifeCycleRuntime)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.0.5")
}
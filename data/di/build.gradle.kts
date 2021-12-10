plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
    id(Hilt.Plugins.hilt)
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"


    defaultConfig {
        minSdk = 23
        targetSdk = 30
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        debug { buildConfigField("String", BuildConfig.baseUrl, BuildConfig.baseUrlDebug) }
        release { buildConfigField("String", BuildConfig.baseUrl, BuildConfig.baseUrlRelease) }

    }


    kotlinOptions {
        jvmTarget = "1.8"
    }
    hilt {
        enableExperimentalClasspathAggregation = true
    }
}

dependencies {

    implementation(project(mapOf("path" to ":domain:model")))
    implementation(project(mapOf("path" to ":data:open")))
    implementation(project(mapOf("path" to ":data:remote")))
    implementation(project(mapOf("path" to ":data:repository")))
    implementation(project(mapOf("path" to ":data:remote:model")))
    implementation(Hilt.hiltAndroid)
    implementation(project(mapOf("path" to ":domain:repository:open")))
    kapt(Hilt.hiltCompiler)
    kapt(Hilt.hiltAndroidCompiler)
    implementation(SquareApp.retrofit)
    implementation(SquareApp.retrofitCoroutines)
    implementation(SquareApp.retrofitGson)
    implementation(SquareApp.loginInterceptor)
}

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id(Hilt.Plugins.hilt)
}

android {
    compileSdk = DefaultConfig.projectCompileSdkVersion

    defaultConfig {
        minSdk = DefaultConfig.projectMinSdkVersion
        targetSdk = DefaultConfig.projectTargetSdkVersion
        testInstrumentationRunner = Androidx.testInstrumentationRunner
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JVMTarget.Versions.core
    }
    buildTypes {
        debug { }
        release { }
    }
    hilt {
        enableExperimentalClasspathAggregation = true
    }
}

dependencies {
    implementation(project(mapOf("path" to ":domain:model")))
    implementation(project(mapOf("path" to ":data:open")))
    implementation(project(mapOf("path" to ":data:repository:open")))
    implementation(project(mapOf("path" to ":data:repository:model")))
    implementation("androidx.core:core-ktx:1.7.0")
    testImplementation("junit:junit:4.13.2")
    implementation(Google.gson)
    implementation(SquareApp.retrofit)
    implementation(SquareApp.retrofitGson)
    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)
    kapt(Hilt.hiltAndroidCompiler)
    implementation(Kotlin.coroutinesCore)
}
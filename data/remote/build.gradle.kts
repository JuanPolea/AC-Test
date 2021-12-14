plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
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
    implementation(project(":domain:model"))
    implementation(project(":data:open"))
    implementation(Kotlin.coroutinesCore)
    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)
    kapt(Hilt.hiltAndroidCompiler)
    implementation(SquareApp.retrofit)
    implementation(SquareApp.retrofitCoroutines)
    implementation(SquareApp.retrofitGson)
    implementation(SquareApp.loginInterceptor)
    implementation(Arrow.core)
    kapt(Arrow.meta)
    implementation(Arrow.sintax)
}
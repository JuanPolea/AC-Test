plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
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
    implementation(project(":data:di"))
    implementation(project(":domain:repository:di"))
    implementation(project(":domain:repository:open"))
    implementation(project(":domain:usecase:open"))
    implementation(Kotlin.coroutinesCore)
    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)
    kapt(Hilt.hiltAndroidCompiler)
    implementation(Kotlin.coroutinesCore)
}
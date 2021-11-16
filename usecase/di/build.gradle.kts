plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
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
}

dependencies {
    implementation(project(":data:open"))
    implementation(project(":data:di"))
    implementation(project(":domain:model"))
    implementation(project(":usecase:implementation"))
    implementation(project(":usecase:open"))
    implementation(Kotlin.coroutinesCore)
    implementation(Hilt.hiltAndroid)
    implementation(Hilt.hiltCompiler)
    implementation(Hilt.hiltAndroidCompiler)
    implementation(Kotlin.coroutinesCore)
}
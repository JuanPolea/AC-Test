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
    implementation(project(":domain:model"))
    implementation(project(":data:repository:model"))
    implementation(project(":data:open"))
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation(project(mapOf("path" to ":data:repository:open")))
    testImplementation("junit:junit:4.13.2")
    implementation(Google.gson)
    implementation(SquareApp.retrofit)
    implementation(SquareApp.retrofitGson)
    implementation(Hilt.hiltAndroid)
    implementation(Hilt.hiltCompiler)
    implementation(Hilt.hiltAndroidCompiler)
    implementation(Kotlin.coroutinesCore)

}
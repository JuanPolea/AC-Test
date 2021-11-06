plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `kotlin-dsl-precompiled-script-plugins`
}
buildscript {
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
        google()
        gradlePluginPortal()
    }
    dependencies {
        classpath(Gradle.androidBuildGradle)
        classpath(Kotlin.plugin)
        classpath(Androidx.navigationPlugin)
        classpath(Detekt.gradlePlugin)
        classpath(Hilt.gradlePlugin)
    }
    configurations.all {
        resolutionStrategy {
            //force all dependencies to use the same version
            force("androidx.media:media:1.0.0")
        }
    }
}
allprojects {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
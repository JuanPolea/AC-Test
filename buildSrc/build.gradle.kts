plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `kotlin-dsl-precompiled-script-plugins`
}

gradlePlugin {
    plugins {
        register("config-plugin") {
            id = "config-plugin"
            implementationClass = "com.jfmr.ac.test.CommonModulePlugin"
        }
    }
}
buildscript {

    repositories {
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
    }

}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    kotlinOptions.languageVersion = JavaVersion.VERSION_1_4.toString()
}
dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.android.tools.build:gradle:7.0.4")
    implementation("com.android.tools.build:gradle-api:7.0.4")
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-stdlib", version = "1.5.31")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
}
tasks.test {
    // Use the built-in JUnit support of Gradle.
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
repositories {
    google()
    mavenCentral()
    maven { setUrl("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
    gradlePluginPortal()
}

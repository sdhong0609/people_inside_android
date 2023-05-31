import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.beside153.peopleinside.Configuration
import com.beside153.peopleinside.Libraries

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint") version "11.3.2"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.beside153.peopleinside"
    compileSdk = Configuration.compileSdk

    defaultConfig {
        applicationId = "com.beside153.peopleinside"
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
}

tasks.named("preBuild").dependsOn("ktlintCheck")
tasks.named("preBuild").dependsOn("detekt")

dependencies {
    implementation(Libraries.KTX.CORE)
    implementation(Libraries.AndroidX.APP_COMPAT)
    implementation(Libraries.AndroidX.MATERIAL)
    implementation(Libraries.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Libraries.Firebase.ANALYTICS)
    implementation(Libraries.Firebase.CRASHYTICS)
    testImplementation(Libraries.Test.JUNIT)
    androidTestImplementation(Libraries.AndroidTest.EXT_JUNIT)
    androidTestImplementation(Libraries.AndroidTest.ESPRESSO_CORE)
}

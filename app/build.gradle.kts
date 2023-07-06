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
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.21"
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
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
        getByName("debug") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        dataBinding = true
    }
}

tasks.named("preBuild").dependsOn("ktlintCheck")

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

    // Navigation
    implementation(Libraries.Navigation.NAVIGATION_FRAGMENT)
    implementation(Libraries.Navigation.NAVIGATION_UI)

    // Retrofit2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

    // kotlinx-serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")

    // Kakao Login
    implementation("com.kakao.sdk:v2-user:2.14.0")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.fragment:fragment-ktx:1.6.0")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")
}

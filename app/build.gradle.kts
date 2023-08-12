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
    id("kotlinx-serialization")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
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
//        getByName("debug") {
//            isMinifyEnabled = true
//            isShrinkResources = true
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
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

dependencies {
    // KTX
    implementation(Libraries.KTX.core)

    // AndroidX
    implementation(Libraries.AndroidX.appcompat)
    implementation(Libraries.AndroidX.material)
    implementation(Libraries.AndroidX.constraintlayout)

    // Test
    testImplementation(Libraries.Test.junit)

    // AndroidTest
    androidTestImplementation(Libraries.AndroidTest.ext_junit)
    androidTestImplementation(Libraries.AndroidTest.espresso_core)

    // Firebase
    implementation(platform(Libraries.Firebase.firebase_bom))
    implementation(Libraries.Firebase.analytics)
    implementation(Libraries.Firebase.crashlytics)

    // Navigation
    implementation(Libraries.Navigation.navigation_fragment)
    implementation(Libraries.Navigation.navigation_ui)

    // Retrofit2
    implementation(Libraries.Retrofit2.retrofit2)
    implementation(Libraries.Retrofit2.retrofit2_converter)

    // Kotlinx Serialization Json
    implementation(Libraries.KotlinxSerializationJson.kotlinx_serialization_json)

    // Glide
    implementation(Libraries.Glide.glide)

    // Kakao Login
    implementation(Libraries.KakaoLogin.kakao_login)

    // ViewModel
    implementation(Libraries.ViewModel.viewmodel)
    implementation(Libraries.ViewModel.activity_ktx)
    implementation(Libraries.ViewModel.fragment_ktx)

    // Timber
    implementation(Libraries.Timber.timber)

    // Hilt
    implementation(Libraries.Hilt.hilt)
    kapt(Libraries.Hilt.hilt_compiler)

    // Flexbox Layout
    implementation("com.google.android.flexbox:flexbox:3.0.0")
}

kapt {
    correctErrorTypes = true
}

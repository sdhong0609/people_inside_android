package com.beside153.peopleinside

object Versions {

    // KTX
    const val core = "1.7.0"

    // AndroidX
    const val appcompat = "1.6.1"
    const val material = "1.9.0"
    const val constraintlayout = "2.1.4"

    // Test
    const val junit = "4.13.2"

    // Android Test
    const val ext_junit = "1.1.5"
    const val espresso_core = "3.5.1"

    // Firebase
    const val firebase_bom = "32.1.1"
    const val crashlytics = "18.3.7"

    // Navigation
    const val navigation = "2.5.3"

    // Retrofit2
    const val retrofit2 = "2.9.0"
    const val retrofit2_converter = "1.0.0"

    // Kotlinx Serialization Json
    const val kotlinx_serialization_json = "1.5.1"

    // Glide
    const val glide = "4.15.1"

    // Kakao Login
    const val kakao_login = "2.14.0"

    // ViewModel
    const val viewmodel = "2.6.1"
    const val activity_ktx = "1.7.2"
    const val fragment_ktx = "1.6.0"

    // Timber
    const val timber = "5.0.1"

    // Hilt
    const val hilt = "2.44.2"
    const val hilt_compiler = "2.44"
}

object Libraries {

    object KTX {
        const val core = "androidx.core:core-ktx:${Versions.core}"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val material = "com.google.android.material:material:${Versions.material}"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
    }

    object AndroidTest {
        const val ext_junit = "androidx.test.ext:junit:${Versions.ext_junit}"
        const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
    }

    object Firebase {
        const val firebase_bom = "com.google.firebase:firebase-bom:${Versions.firebase_bom}"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"
        const val crashlytics = "com.google.firebase:firebase-crashlytics:${Versions.crashlytics}"
    }

    object Navigation {
        const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    }

    object Retrofit2 {
        const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
        const val retrofit2_converter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofit2_converter}"
    }

    object KotlinxSerializationJson {
        const val kotlinx_serialization_json =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinx_serialization_json}"
    }

    object Glide {
        const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    }

    object KakaoLogin {
        const val kakao_login = "com.kakao.sdk:v2-user:${Versions.kakao_login}"
    }

    object ViewModel {
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewmodel}"
        const val activity_ktx = "androidx.activity:activity-ktx:${Versions.activity_ktx}"
        const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"
    }

    object Timber {
        const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    }

    object Hilt {
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt_compiler}"
    }
}

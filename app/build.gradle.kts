import com.beside153.peopleinside.Configuration
import com.beside153.peopleinside.Libraries

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
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
}

dependencies {
    implementation(Libraries.KTX.CORE)
    implementation(Libraries.AndroidX.APP_COMPAT)
    implementation(Libraries.AndroidX.MATERIAL)
    implementation(Libraries.AndroidX.CONSTRAINT_LAYOUT)
    testImplementation(Libraries.Test.JUNIT)
    androidTestImplementation(Libraries.AndroidTest.EXT_JUNIT)
    androidTestImplementation(Libraries.AndroidTest.ESPRESSO_CORE)
}

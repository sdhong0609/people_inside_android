package com.beside153.peopleinside

object Versions {

    // KTX
    const val CORE = "1.7.0"

    // AndroidX
    const val APP_COMPAT = "1.6.1"
    const val MATERIAL = "1.9.0"
    const val CONSTRAINT_LAYOUT = "2.1.4"

    // Test
    const val JUNIT = "4.13.2"

    // Android Test
    const val EXT_JUNIT = "1.1.5"
    const val ESPRESSO_CORE = "3.5.1"
}

object Libraries {

    object KTX {
        const val CORE = "androidx.core:core-ktx:${Versions.CORE}"
    }

    object AndroidX {
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    }

    object Test {
        const val JUNIT = "junit:junit:${Versions.JUNIT}"
    }

    object AndroidTest {
        const val EXT_JUNIT = "androidx.test.ext:junit:${Versions.EXT_JUNIT}"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    }
}
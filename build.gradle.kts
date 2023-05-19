// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.1" apply false
    id("com.android.library") version "8.0.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.3.2"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
}

buildscript {
    dependencies {
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.android.tools.build:gradle:8.0.1")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.5")
    }
}

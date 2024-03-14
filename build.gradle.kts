// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}

buildscript {
    repositories {
        maven(url = "https://chaquo.com/maven")
    }
    dependencies {
        classpath("com.chaquo.python:gradle:15.0.1") // Make sure to use the latest version
    }
}

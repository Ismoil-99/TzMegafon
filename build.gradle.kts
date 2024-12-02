// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {

    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidNavigationSafeargs) apply false
    alias(libs.plugins.hiltPlugin) apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
}
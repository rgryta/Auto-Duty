// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.venniktech) apply false
    alias(libs.plugins.ben.manes) apply true
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
}
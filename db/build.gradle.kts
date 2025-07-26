plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}


group = "eu.gryta"
val projectName: String = "autoduty"

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-opt-in=kotlin.time.ExperimentalTime")
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    androidLibrary {
        namespace = "$group.$projectName.db"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("stdlib"))

                implementation(libs.serialization.core)
                implementation(libs.serialization.json)

                implementation(libs.room.runtime)
                implementation(libs.sqlite.bundled)
            }
        }
    }
}

dependencies {
    add("kspAndroid", libs.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

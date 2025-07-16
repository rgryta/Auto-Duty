import java.io.FileInputStream
import java.util.Properties

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

val properties = Properties()
try {
    properties.load(
        FileInputStream(
            file(
                rootProject.projectDir.toPath().resolve("gpr.properties")
            )
        )
    )
} catch (e: Exception) {
    logger.error(
        "Properties not found! Checked under: ${
            rootProject.projectDir.toPath().resolve("gpr.properties")
        }"
    )
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            name = "RGryta GH"
            url = uri("https://maven.pkg.github.com/rgryta/*")
            credentials {
                username = properties.getProperty("gpr.user", System.getenv("GPR_USERNAME"))
                password = properties.getProperty("gpr.key", System.getenv("GPR_TOKEN"))
            }
            content {
                includeGroup("eu.gryta")
            }
        }
    }
}

rootProject.name = "Auto Duty"
include(":app")
 
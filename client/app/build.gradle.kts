import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

val isAppleSilicon =
        DefaultNativePlatform.getCurrentOperatingSystem().isMacOsX() &&
                DefaultNativePlatform.getCurrentArchitecture().isArm64()
/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.3/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()

    // use custom jar files for Apple Silicon
    if (isAppleSilicon) {
        flatDir { dirs("../libs") }
    }
}

dependencies {
    // Jaylib
    if (isAppleSilicon) {
        // use the custom jar files for Apple Silicon
        print("Apple silicon detected, using custom-built jar files.\n")

        // For C++ Types
        implementation(files("libs/javacpp.jar"))
        implementation("uk.co.electonstudio.jaylib:jaylib-4.2.0")
        implementation("uk.co.electonstudio.jaylib:jaylib-natives-macosx-arm64-4.2.0")
    } else {
        implementation("uk.co.electronstudio.jaylib:jaylib:4.2.+")
    }

    // JSON
    implementation("com.google.code.gson:gson:2.10.1")
}

// Apply a specific Java toolchain to ease working on different environments.
java { toolchain { languageVersion.set(JavaLanguageVersion.of(17)) } }

application {
    // Define the main class for the application.
    mainClass.set("com.ezntek.beanmaths.App")

    // jaylib
    if (DefaultNativePlatform.getCurrentOperatingSystem().isMacOsX()) {
        applicationDefaultJvmArgs = mutableListOf("-XstartOnFirstThread")
    }
}

distributions { main { contents { into("resources") { from("resources") } } } }

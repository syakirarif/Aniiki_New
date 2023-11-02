plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}
// Required since Gradle 4.10+.
repositories {
    google()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
    gradlePluginPortal()
}

kotlinDslPluginOptions {
//    experimentalWarning.set(false)
}

dependencies {
//    implementation("com.android.tools.build:gradle:7.2.1")
//    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    implementation("com.squareup:javapoet:1.13.0")
}
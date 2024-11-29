import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")

//    id("dagger.hilt.android.plugin")
    id("com.google.dagger.hilt.android") // Hilt 2.50

//    id("com.google.gms.google-services")
//    id("com.google.firebase.crashlytics")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {

    val properties = File(rootDir, "keystore.properties").inputStream().use {
        Properties().apply { load(it) }
    }
    val mStorePassword = properties.getValue("storePassword") as String
    val mKeyPassword = properties.getValue("keyPassword") as String
    val mKeyAlias = properties.getValue("keyAlias") as String
    val mStoreFile = properties.getValue("storeFile") as String

    signingConfigs {
        create("release") {
            keyAlias = mKeyAlias
            keyPassword = mKeyPassword
            storeFile = file(mStoreFile)
            storePassword = mStorePassword
        }
    }

    namespace = BuildAndroidConfig.APPLICATION_ID
//    compileSdk = BuildAndroidConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = BuildAndroidConfig.APPLICATION_ID
//        minSdk = BuildAndroidConfig.MIN_SDK_VERSION
//        targetSdk = BuildAndroidConfig.TARGET_SDK_VERSION
        versionCode = BuildAndroidConfig.VERSION_CODE
        versionName = BuildAndroidConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeRelease.isTestCoverageEnabled
            signingConfig = signingConfigs.getByName("release")
        }
        getByName(BuildType.DEBUG) {
            applicationIdSuffix = BuildTypeDebug.applicationIdSuffix
            versionNameSuffix = BuildTypeDebug.versionNameSuffix
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeDebug.isTestCoverageEnabled
        }
    }

//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_17
//    }
//    kotlinOptions {
//        val options = this
//        options.jvmTarget = "17"
//    }
    buildFeatures {
//        viewBinding = true
//        compose = true
        buildConfig = true
    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.4"
//    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

apply(from = "$rootDir/buildSrc/android-config-module.gradle")

dependencies {

    implementation(project(BuildModules.Commons.CORE))
    implementation(project(BuildModules.Features.LOGIN))
    implementation(project(BuildModules.Features.HOME))

    // Hilt
//    implementation(Google.dagger.hilt.android)
//    kapt(Google.dagger.hilt.compiler)
//    kapt(Google.dagger.hilt.android.compiler)
//    implementation("com.google.dagger:hilt-android:2.48")
//    ksp("com.google.dagger:hilt-compiler:2.48")
    implementation("com.google.dagger:hilt-android:2.50")
    ksp("com.google.dagger:hilt-compiler:2.50")

}

//kapt {
//    correctErrorTypes = true
//}
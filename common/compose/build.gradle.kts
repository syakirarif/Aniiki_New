plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
//    id("com.google.gms.google-services")
//    id("com.google.firebase.crashlytics")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.syakirarif.aniiki.compose"
}

apply(from = "$rootDir/buildSrc/android-config-module.gradle")
apply(from = "$rootDir/buildSrc/common-dependencies.gradle")

dependencies {
    // Compose
    api(AndroidX.compose.ui)
    api(AndroidX.compose.ui.graphics)
    api(AndroidX.compose.ui.toolingPreview)
    api(AndroidX.compose.material3)
    api(AndroidX.activity.compose)
    api(AndroidX.lifecycle.viewModelCompose)
    api(AndroidX.lifecycle.runtime.compose)
    api(AndroidX.hilt.navigationCompose)
    api(AndroidX.compose.runtime.liveData)
    api(AndroidX.paging.compose)
    api(AndroidX.constraintLayout.compose)
    api(COIL.compose)
    api(AndroidX.palette)
    api(AndroidX.navigation.compose)

    
    // Landscapist
    val landscapistVersion = "2.2.10"
    api("com.github.skydoves:landscapist-coil:$landscapistVersion")
    api("com.github.skydoves:landscapist-glide:$landscapistVersion")
    api("com.github.skydoves:landscapist-animation:$landscapistVersion")
    api("com.github.skydoves:landscapist-placeholder:$landscapistVersion")
    api("com.github.skydoves:landscapist-palette:$landscapistVersion")
}
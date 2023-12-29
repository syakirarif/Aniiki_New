plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    //    id("dagger.hilt.android.plugin")
    id("com.google.dagger.hilt.android") // Hilt 2.50

//    id("com.google.gms.google-services")
//    id("com.google.firebase.crashlytics")
    id("com.google.devtools.ksp")
}

apply(from = "$rootDir/buildSrc/android-config-module.gradle")
apply(from = "$rootDir/buildSrc/common-dependencies.gradle")

android {
    namespace = "com.syakirarif.aniiki.core"
}

dependencies {
    api(project(":common:apiservice"))
    api(project(":common:compose"))

    api(AndroidX.core.ktx)
    api(AndroidX.fragment.ktx)
    api(AndroidX.paging.runtimeKtx)
    api(AndroidX.navigation.uiKtx)
    api(AndroidX.navigation.fragmentKtx)
    api(AndroidX.navigation.common)
    api(AndroidX.swipeRefreshLayout)

    api(AndroidX.lifecycle.liveDataKtx)
    api(AndroidX.lifecycle.viewModelKtx)
    api(AndroidX.lifecycle.runtime.ktx)
    api(KotlinX.coroutines.android)

    // The Play Core library got partitioned/split into multiple per-feature libraries.
    //FIXME: Migrate to the right Play Library
    // There are new Google.android.play.*** dependency notations,
    // but make sure to read the migration guide before:
    // https://developer.android.com/guide/playcore#playcore-migration
//    api("com.google.android.play:core:_")
    api(Google.android.play.appUpdateKtx)

    // Room
    ksp(AndroidX.room.compiler)
    annotationProcessor(AndroidX.room.compiler)

    // Hawk
    api("com.orhanobut:hawk:2.0.1")

    // Hilt
//    api(Google.dagger.hilt.android)
//    kapt(Google.dagger.hilt.compiler)

//    implementation("com.google.dagger:hilt-android:2.48")
//    ksp("com.google.dagger:hilt-compiler:2.48")

    // Coil
    api(COIL)

    // Kiel
    api("io.github.ibrahimyilmaz:kiel:1.2.1")

    // Hawk
    implementation("com.orhanobut:hawk:2.0.1")

    ////////////// Chucker
    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")

    // Lottie
    api("com.airbnb.android:lottie:5.2.0")

    // Firebase BoM
//    api(platform("com.google.firebase:firebase-bom:32.3.1"))

    // Crashlytics
//    api("com.google.firebase:firebase-crashlytics-ktx")
//    api("com.google.firebase:firebase-analytics-ktx")
}
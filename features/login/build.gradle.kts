plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
//    id("com.google.gms.google-services")
//    id("com.google.firebase.crashlytics")
    id("com.google.devtools.ksp")
}

apply(from = "$rootDir/buildSrc/android-config-module.gradle")
apply(from = "$rootDir/buildSrc/feature-dependencies.gradle")

android {
    namespace = "com.aniiki.features.login"
    buildTypes.forEach {
//        it.buildConfigStringField("MOVIE_DB_API_KEY", "9935e9c0d3faf47164665749d78bd644")
//        it.buildConfigStringField("MOVIE_DB_BASE_URL", "https://api.themoviedb.org/3/")
    }
}

dependencies {

}
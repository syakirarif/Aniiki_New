import com.android.build.api.dsl.LibraryDefaultConfig
import java.util.Locale

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
//    id("com.google.gms.google-services")
//    id("com.google.firebase.crashlytics")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "com.syakirarif.aniiki.apiservice"
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        buildConfigFieldFromGradleProperty("apiBaseUrl")
    }
}

apply(from = "$rootDir/buildSrc/android-config-module.gradle")
apply(from = "$rootDir/buildSrc/common-dependencies.gradle")

dependencies {
    // Retrofit
    api(Square.retrofit2.retrofit)
    api(Square.retrofit2.converter.gson)
    api(Square.retrofit2.adapter.rxJava2)
    api(Square.okHttp3.loggingInterceptor)

    // Timber
    api(JakeWharton.timber)

    // Sandwich
    api("com.github.skydoves:sandwich:1.3.9")

    implementation("com.github.skydoves:retrofit-adapters-paging:1.0.8")

    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")

    api("com.beust:klaxon:5.5")
}

/*
Takes value from Gradle project property and sets it as Android build config property eg.
apiToken variable present in the settings.gradle file will be accessible as BuildConfig.GRADLE_API_TOKEN in the app.
 */
fun LibraryDefaultConfig.buildConfigFieldFromGradleProperty(gradlePropertyName: String) {
    val propertyValue = project.properties[gradlePropertyName] as? String
    checkNotNull(propertyValue) { "Gradle property $gradlePropertyName is null" }

    val androidResourceName =
        "GRADLE_${gradlePropertyName.toSnakeCase()}".uppercase(Locale.getDefault())
    buildConfigField("String", androidResourceName, propertyValue)
}

fun String.toSnakeCase() =
    this.split(Regex("(?=[A-Z])")).joinToString("_") { it.lowercase(Locale.getDefault()) }
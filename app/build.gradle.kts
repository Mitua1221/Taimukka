import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
}

android {
    signingConfigs {
        val localProps = gradleLocalProperties(rootDir)
        getByName("debug") {
            storeFile = file(localProps.getProperty("sign_debug"))
            storePassword = localProps.getProperty("pass_debug")
            keyAlias = localProps.getProperty("alias_debug")
            keyPassword = localProps.getProperty("pass_debug")
        }
        create("release") {
            storeFile = file(localProps.getProperty("sign_release"))
            storePassword = localProps.getProperty("pass_release")
            keyAlias = localProps.getProperty("alias_release")
            keyPassword = localProps.getProperty("pass_release")
        }
    }
    namespace = "com.arjental.taimukka"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.arjental.taimukka"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "0.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
            manifestPlaceholders.putAll(
                mapOf(
                    "appIcon" to "@mipmap/ic_launcher",
                    "appRoundIcon" to "@mipmap/ic_launcher_round",
                )
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
            versionNameSuffix = "-debug"
            applicationIdSuffix = ".debug"
            manifestPlaceholders.putAll(
                mapOf(
                    "appIcon" to "@mipmap/ic_launcher_debug",
                    "appRoundIcon" to "@mipmap/ic_launcher_debug_round",
                )
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0-alpha02"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation ("androidx.core:core-ktx:1.9.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation ("androidx.core:core-splashscreen:1.0.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.4")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.0")

    //date and time
    implementation ("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation ("org.ocpsoft.prettytime:prettytime:5.0.4.Final")

    //Compose
    implementation ("androidx.compose.foundation:foundation:1.4.0-alpha03")
    implementation ("androidx.compose.runtime:runtime:1.4.0-alpha03")
    implementation ("androidx.compose.ui:ui:1.3.2")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.3.2")
    implementation ("androidx.compose.ui:ui-tooling:1.4.0-alpha03")
    implementation ("androidx.activity:activity-compose:1.6.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha03")
    implementation ("androidx.compose.runtime:runtime-livedata:1.3.2")
    implementation ("androidx.compose.material3:material3:1.1.0-alpha03")
    implementation ("androidx.compose.material3:material3-window-size-class:1.1.0-alpha03") //the same as androidx.compose.material3:material3
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")


    //implementation "androidx.compose.material:material:1.4.0-alpha03"

    implementation ("com.google.accompanist:accompanist-adaptive:0.28.0")
    implementation ("com.google.accompanist:accompanist-permissions:0.28.0")

    //pagers compose
//    implementation "com.google.accompanist:accompanist-pager:0.28.0"
//    implementation "com.google.accompanist:accompanist-pager-indicators:0.28.0"

    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.3.2")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.3.2")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.3.2")

    //voyager
    implementation ("cafe.adriel.voyager:voyager-navigator:1.0.0-rc03")
    implementation ("cafe.adriel.voyager:voyager-bottom-sheet-navigator:1.0.0-rc03")
    implementation ("cafe.adriel.voyager:voyager-tab-navigator:1.0.0-rc03")
    implementation ("cafe.adriel.voyager:voyager-transitions:1.0.0-rc03")
    implementation ("cafe.adriel.voyager:voyager-androidx:1.0.0-rc03")
    implementation ("cafe.adriel.voyager:voyager-hilt:1.0.0-rc03")

    //Coroutines

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")

    //Workers

    implementation ("androidx.work:work-runtime:2.7.1")
    implementation ("androidx.work:work-runtime-ktx:2.7.1")

    //Room
    implementation ("androidx.room:room-runtime:2.4.3")
    kapt ("androidx.room:room-compiler:2.4.3")
    implementation ("androidx.room:room-ktx:2.4.3")
    kapt ("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")

    //Dagger 2
    kapt ("com.google.dagger:dagger-compiler:2.44.2")
    implementation ("com.google.dagger:dagger:2.44.2")
    kapt ("com.google.dagger:dagger-compiler:2.44.2")
    implementation ("com.google.dagger:dagger-android-support:2.43.2")
    kapt ("com.google.dagger:dagger-android-processor:2.43.2")

    //Splash
    implementation ("androidx.core:core-splashscreen:1.0.0")

    //Immutable collections
    implementation ("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

}

kapt {
    correctErrorTypes = true
}
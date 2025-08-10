import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.aboutlibraries)
    alias(libs.plugins.room)
}

android {
    namespace = "com.classictoon.novel"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.classictoon.novel"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 13
        versionName = "1.7.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = false

            proguardFiles("proguard-rules.pro")
        }

        create("release-debug") {
            initWith(getByName("release"))
            applicationIdSuffix = ".release.debug"
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
    }
}

aboutLibraries {
    registerAndroidTasks = false
    prettyPrint = true
    gitHubApiToken = gradleLocalProperties(rootDir, providers)["github-key"] as? String

    filterVariants = arrayOf("debug", "release", "release-debug")
    excludeFields = arrayOf("generated", "funding", "description")
}

dependencies {

    // Core AndroidX
    implementation(libs.bundles.androidx.core)

    // Compose
    implementation(libs.bundles.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Accompanist
    implementation(libs.accompanist.swiperefresh)

    // Dagger - Hilt
    implementation(libs.bundles.hilt)
    implementation(libs.hilt.compiler)
    ksp(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)

    // Room
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    // Datastore (Settings)
    implementation(libs.androidx.datastore.preferences)

    // Splash Screen API
    implementation(libs.androidx.core.splashscreen)

    // Language Switcher
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.appcompat.resources)

    // File handling
    implementation(libs.anggrayudi.storage)
    implementation(libs.tomroush.pdfbox.android)

    // Parsing
    implementation(libs.jsoup)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.commonmark)

    // UI Libraries
    implementation(libs.coil.compose)
    implementation(libs.reorderable)
    implementation(libs.lazycolumnscrollbar)

    // About Libraries
    implementation(libs.bundles.aboutlibraries)

    // JSON
    implementation(libs.gson)
}
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
    
    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    
    // Testing Dependencies
    testImplementation(kotlin("test"))
    
    // JUnit 4
    testImplementation("junit:junit:4.13.2")
    
    // MockK for mocking
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("io.mockk:mockk-android:1.13.8")
    
    // Kotlin Coroutines Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    
    // Robolectric for Android testing
    testImplementation("org.robolectric:robolectric:4.11.1")
    
    // MockWebServer for API testing
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    
    // Truth assertions (alternative to JUnit assertions)
    testImplementation("com.google.truth:truth:1.1.5")
    
    // Turbine for testing flows
    testImplementation("app.cash.turbine:turbine:1.0.0")
    
    // AndroidX Test
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("androidx.test:runner:1.5.2")
    testImplementation("androidx.test:rules:1.5.0")
    testImplementation("androidx.test.ext:junit:1.1.5")
    
    // AndroidX Test Espresso
    testImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    testImplementation("androidx.test.espresso:espresso-intents:3.5.1")
    
    // Compose Testing
    testImplementation("androidx.compose.ui:ui-test-junit4:1.5.4")
    testImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")
    
    // Hilt Testing
    testImplementation("com.google.dagger:hilt-android-testing:2.48")
    kspTest("com.google.dagger:hilt-android-compiler:2.48")
    
    // Room Testing
    testImplementation("androidx.room:room-testing:2.6.1")
    
    // Arch Core Testing
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    
    // Mockito (alternative to MockK)
    testImplementation("org.mockito:mockito-core:5.7.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    
    // JSON Testing
    testImplementation("org.json:json:20231013")
    
    // WireMock for API mocking (alternative to MockWebServer)
    testImplementation("org.wiremock:wiremock-standalone:3.2.0")
    
    // Test Rules
    testImplementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("androidx.test.ext:truth:1.5.0")
}
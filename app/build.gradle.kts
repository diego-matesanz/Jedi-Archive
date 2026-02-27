plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.diego.matesanz.jedi.archive"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.diego.matesanz.jedi.archive"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Feature modules
    implementation(project(":feature:search"))
    implementation(project(":feature:detail"))
    implementation(project(":feature:settings"))

    // Core modules
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:network"))
    implementation(project(":core:datastore"))
    implementation(project(":core:navigation"))

    // Agent System (for development/debugging)
    implementation(project(":core:agents"))
    implementation(project(":agents:architect"))
    implementation(project(":agents:engineer"))
    implementation(project(":agents:uiux"))
    implementation(project(":agents:api"))
    implementation(project(":agents:qa"))

    // Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // TODO: Add Hilt in future

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // DI Manual - Network dependencies for manual DI in NavHost
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
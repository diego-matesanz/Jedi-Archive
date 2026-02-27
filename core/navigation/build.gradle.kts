plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.diego.matesanz.jedi.archive.core.navigation"
    compileSdk = 36

    defaultConfig {
        minSdk = 33
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
    // Core modules
    implementation(project(":core:domain"))

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)

    // Navigation
    implementation(libs.androidx.navigation.compose)
}

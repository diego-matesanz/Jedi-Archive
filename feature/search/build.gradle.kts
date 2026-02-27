plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.diego.matesanz.jedi.archive.feature.search"
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
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)

    debugImplementation(libs.androidx.compose.ui.tooling)
}

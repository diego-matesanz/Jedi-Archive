plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.diego.matesanz.jedi.archive.core.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 33
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:network"))

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}

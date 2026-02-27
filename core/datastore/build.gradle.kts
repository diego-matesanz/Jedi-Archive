plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.diego.matesanz.jedi.archive.core.datastore"
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
    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
}

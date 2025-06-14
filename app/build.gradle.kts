plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.tripnesia.mobile"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tripnesia.mobile"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    implementation("androidx.compose.foundation:foundation:1.4.0")// Atau versi terbaru yang tersedia
    implementation("androidx.compose.animation:animation:1.4.0")
    implementation("io.coil-kt:coil-compose:2.1.0")
    implementation("androidx.compose.material:material-icons-extended:1.4.0")
    implementation("androidx.core:core-ktx:1.13.1") // Versi bisa berbeda
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0") // Versi bisa berbeda
    implementation("androidx.activity:activity-compose:1.9.0") // Versi bisa berbeda
    implementation(platform("androidx.compose:compose-bom:2024.05.00")) // Selalu gunakan BOM Compose terbaru
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-core") // Untuk ikon dasar
    implementation("androidx.compose.material:material-icons-extended") // Untuk ikon tambahan
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.navigation:navigation-compose:2.7.5")
//=======
//    dependencies {
//        // Jetpack Compose
//        implementation("androidx.core:core-ktx:1.13.1") // Versi bisa berbeda
//        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0") // Versi bisa berbeda
//        implementation("androidx.activity:activity-compose:1.9.0") // Versi bisa berbeda
//        implementation(platform("androidx.compose:compose-bom:2024.05.00")) // Selalu gunakan BOM Compose terbaru
//        implementation("androidx.compose.ui:ui")
//        implementation("androidx.compose.ui:ui-graphics")
//        implementation("androidx.compose.ui:ui-tooling-preview")
//        implementation("androidx.compose.material3:material3")
//        implementation("androidx.compose.material:material-icons-core") // Untuk ikon dasar
//        implementation("androidx.compose.material:material-icons-extended") // Untuk ikon tambahan
//        testImplementation("junit:junit:4.13.2")
//        androidTestImplementation("androidx.test.ext:junit:1.1.5")
//        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//        androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))
//        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//        debugImplementation("androidx.compose.ui:ui-tooling")
//        debugImplementation("androidx.compose.ui:ui-test-manifest")
//    }
//>>>>>>> main
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.ads.mobile.sdk)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
plugins {

    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    //plugin for firebase
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "com.depi.budgetapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.depi.budgetapp"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {


    // Jetpack Compose integration
    implementation("androidx.navigation:navigation-compose")

    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment:")
    implementation("androidx.navigation:navigation-ui")

    // Feature module support for Fragments
    implementation("androidx.navigation:navigation-dynamic-features-fragment:")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.2")
    implementation("androidx.core:core-i18n:1.0.0-alpha01")
    implementation("com.google.firebase:protolite-well-known-types:18.0.0")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing")
    //firebase dependency
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    implementation("com.google.firebase:firebase-analytics")

    implementation( "androidx.room:room-runtime:2.5.0")

    // For Kotlin use kapt instead of annotationProcessor

    // optional - RxJava2 support for Room
    implementation ("androidx.room:room-rxjava2:2.5.0")

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation( "androidx.room:room-guava:2.5.0")

    // Test helpers
    testImplementation ("androidx.room:room-testing:2.5.0")

    //graph
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity-ktx:1.9.2")
    implementation("androidx.navigation:navigation-fragment:2.8.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:")
    kapt("androidx.room:room-compiler:2.6.1")

    implementation ("androidx.room:room-runtime:2.6.1") // or latest version
    implementation ("androidx.room:room-ktx:2.6.1") // Optional, for Kotlin extensions
}



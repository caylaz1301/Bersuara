plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
    }
    namespace = "com.example.bersuara"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bersuara"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-auth:23.1.0")
    implementation ("com.google.firebase:firebase-database:21.0.0")
    implementation ("com.google.firebase:firebase-storage:21.0.1")
    implementation ("com.google.firebase:firebase-firestore:25.1.1")
        implementation ("androidx.camera:camera-camera2:1.4.1")
        implementation ("androidx.camera:camera-lifecycle:1.4.1")
        implementation ("androidx.camera:camera-view:1.4.1")
    implementation ("com.google.firebase:firebase-database:21.0.0")


}



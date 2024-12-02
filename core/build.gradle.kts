import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.pessoto.stockmarket.core"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val properties = Properties()
        properties.load(FileInputStream(file("apikey.properties")))
        buildConfigField("String", "API_KEY", properties.getProperty("API_KEY"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL_API", "\"https://brapi.dev/\"")
        }

        debug {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL_API", "\"https://brapi.dev/\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.javaVersion.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.javaVersion.get())
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }

    testOptions {
        animationsDisabled = true
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    implementation(libs.koin.compose)

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging.interceptor)
}

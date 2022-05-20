import utils.*

plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Base.currentSDK

    defaultConfig {
        minSdk = Base.minSDK
        targetSdk = Base.currentSDK
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
    implementation (Dependencies.Ktx.core)
    implementation (Dependencies.Ktx.activity)
    implementation (Dependencies.Androidx.appCompat)
    implementation (Dependencies.Androidx.material)
    implementation (Dependencies.Androidx.constraintLayout)

    implementation (Dependencies.LifeCycle.runtime)
    implementation (Dependencies.LifeCycle.livedata)
    implementation (Dependencies.LifeCycle.viewModel)

    implementation (Dependencies.Coroutines.core)
    implementation (Dependencies.Coroutines.android)

    implementation (Dependencies.Binding.delegate)
}
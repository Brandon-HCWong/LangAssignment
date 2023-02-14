plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.devtools.ksp") version Versions.KSP_PLUGIN_VERSION
}

android {
    namespace = "com.brandonwong.langassignment"
    compileSdk = Versions.SDK_COMPILE_VERSION

    defaultConfig {
        applicationId = "com.brandonwong.langassignment"
        minSdk = Versions.SDK_MIN_VERSION
        targetSdk = Versions.SDK_TARGET_VERSION
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "UNSPLASH_ACCESS_KEY", "\"${Keys.UNSPLASH_ACCESS_KEY}\"")
        buildConfigField("String", "TMBD_ACCESS_KEY", "\"${Keys.TMBD_ACCESS_KEY}\"")
        buildConfigField("String", "TMBD_ACCOUNT_ID", "\"${Keys.TMBD_ACCOUNT_ID}\"")
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = false
            isMinifyEnabled = false
            isDebuggable = true
        }

        getByName("release") {
            isTestCoverageEnabled = false
            isMinifyEnabled = false
            isDebuggable = false
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
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to "*.jar")))

    /** AndroidX **/
    implementation("androidx.core:core-ktx:${Versions.ANDROIDX_CORE_VERSION}")
    implementation("androidx.appcompat:appcompat:${Versions.ANDROIDX_APPCOMPAT_VERSION}")
    implementation("androidx.recyclerview:recyclerview:${Versions.ANDROIDX_RECYCLER_VIEW_VERSION}")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.ANDROIDX_CONSTRAINT_LAYOUT_VERSION}")
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.ANDROIDX_NAVIGATION_VERSION}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.ANDROIDX_NAVIGATION_VERSION}")
    implementation("androidx.activity:activity-ktx:${Versions.ANDROIDX_ACTIVITY_VERSION}")
    implementation("androidx.fragment:fragment-ktx:${Versions.ANDROIDX_FRAGMENT_VERSION}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ANDROIDX_LIFECYCLE_VERSION}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE_VERSION}")
    implementation("androidx.paging:paging-runtime-ktx:${Versions.ANDROIDX_PAGING_VERSION}")

    /** Google **/
    implementation("com.google.android.material:material:${Versions.GOOGLE_MATERIAL_VERSION}")

    /** Kotlin **/
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLIN_COROUTINES_VERSION}")

    /** Third Party Library **/
    implementation("com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}")
    implementation("com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT_VERSION}")
    implementation("com.squareup.okhttp3:okhttp:${Versions.OKHTTP3_VERSION}") {
        isForce = true
    }
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP3_VERSION}")
    implementation("com.squareup.moshi:moshi:${Versions.MOSHI_VERSION}")
    implementation("com.squareup.moshi:moshi-adapters:${Versions.MOSHI_VERSION}")
    implementation("com.github.bumptech.glide:glide:${Versions.GLIDE_VERSION}")
    implementation("com.github.bumptech.glide:annotations:${Versions.GLIDE_VERSION}")
    implementation("com.github.bumptech.glide:okhttp3-integration:${Versions.GLIDE_VERSION}")
    implementation("io.insert-koin:koin-android:${Versions.KOIN_VERSION}")
    implementation("io.insert-koin:koin-android-compat:${Versions.KOIN_VERSION}")

    /** Annotation **/
    ksp("androidx.room:room-compiler:${Versions.ANDROIDX_ROOM_VERSION}")
    kapt("com.github.bumptech.glide:compiler:${Versions.GLIDE_VERSION}")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI_VERSION}")

    /** Unit Test **/
    testImplementation("junit:junit:${Versions.TEST_JUNIT_VERSION}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KOTLIN_COROUTINES_VERSION}")
    testImplementation("io.mockk:mockk:${Versions.TEST_MOCKK_VERSION}")
    testImplementation("io.insert-koin:koin-test:${Versions.KOIN_VERSION}")
    testImplementation("io.insert-koin:koin-test-junit4:${Versions.KOIN_VERSION}")

    /** Automation Test **/
    androidTestImplementation("androidx.test.ext:junit:${Versions.TEST_ANDROIDX_JUNIT_VERSION}")
    androidTestImplementation("androidx.test.ext:junit-ktx:${Versions.TEST_ANDROIDX_JUNIT_VERSION}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.TEST_ANDROIDX_ESPRESSO_VERSION}")
}
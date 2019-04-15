import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

androidExtensions {
    configure(delegateClosureOf<AndroidExtensionsExtension> { isExperimental = true })
}

android {
    defaultConfig {
        applicationId = "com.tutuland.app_red" //This does not have to be the same as the package on the manifest
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFile(getDefaultProguardFile("proguard-android.txt"))
        }

        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFile(getDefaultProguardFile("proguard-android.txt"))
        }
    }
}

dependencies {
    implementation(project(Deps.Feats.details))
    implementation(project(Deps.Feats.list))
    implementation(project(Deps.Libs.actions))
    implementation(project(Deps.Libs.cards))
    implementation(project(Deps.Libs.tracking))
    implementation(project(Deps.Libs.utils))

    implementation(fileTree(mapOf("dir" to "libs", "include" to "*.jar")))
    implementation(Deps.kotlin)
    implementation(Deps.androidx_appcompat)
    implementation(Deps.androidx_core)
    implementation(Deps.androidx_constraintlayout)
    implementation(Deps.androidx_material)
    implementation(Deps.androidx_recyclerview)

    testImplementation(Deps.testlib_junit)

    androidTestImplementation(Deps.testandroidx_runner)
    androidTestImplementation(Deps.testandroidx_rules)
    androidTestImplementation(Deps.testandroidx_espressocore)
}
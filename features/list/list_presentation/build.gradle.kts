plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

dependencies {
    api(project(Deps.Feats.List.contract))
    implementation(Deps.timber)
}

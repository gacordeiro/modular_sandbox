plugins {
    id("com.android.library")
}

dependencies {
    implementation(project(Deps.Feats.List.contract))
    implementation(project(Deps.Libs.cards))
    implementation(project(Deps.Libs.tracking))
    implementation(project(Deps.Libs.utils))

    implementation(Deps.kotlin)
    implementation(Deps.pokemon)
    implementation(Deps.timber)
    implementation(Deps.Rx.java)
}

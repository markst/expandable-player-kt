plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.vanniktech.publish)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
    }
}

android {
    namespace = "dev.markturnip.expandable"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates(
        groupId = "dev.markturnip",
        artifactId = "expandable-player",
        version = project.properties["version"].toString()
    )

    pom {
        name = "Expandable Player"
        description = "A Kotlin Multiplatform Compose modifier that adds an expandable media player to your screen."
        url = "https://github.com/markst/expandable-player-kt"
        licenses {
            license {
                name = "MIT License"
                url = "https://opensource.org/licenses/MIT"
            }
        }
        developers {
            developer {
                id = "markst"
                name = "Mark Turnip"
                url = "https://github.com/markst"
            }
        }
        scm {
            url = "https://github.com/markst/expandable-player-kt"
            connection = "scm:git:git://github.com/markst/expandable-player-kt.git"
            developerConnection = "scm:git:ssh://git@github.com/markst/expandable-player-kt.git"
        }
    }
}
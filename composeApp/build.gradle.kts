import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import com.android.build.api.dsl.ManagedVirtualDevice
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
  alias(libs.plugins.multiplatform)
  alias(libs.plugins.compose)
  alias(libs.plugins.android.application)
  alias(libs.plugins.buildConfig)
  alias(libs.plugins.kotlinx.serialization)
}

kotlin {
  androidTarget {
//    compilations.all {
//      kotlinOptions {
//        jvmTarget = "${JavaVersion.VERSION_1_8}"
//        freeCompilerArgs += "-Xjdk-release=${JavaVersion.VERSION_1_8}"
//      }
//    }
    //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    instrumentedTestVariant {
      sourceSetTree.set(KotlinSourceSetTree.test)
      dependencies {
        debugImplementation(libs.androidx.testManifest)
        implementation(libs.androidx.junit4)
      }
    }
  }

  jvm()

  listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64()
  ).forEach {
    it.binaries.framework {
      baseName = "ComposeApp"
      isStatic = true
    }
  }

  sourceSets {
    all {
      languageSettings {
        optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
        optIn("com.russhwolf.settings.ExperimentalSettingsApi")
        optIn("androidx.compose.material3.ExperimentalMaterial3Api")
        optIn("androidx.compose.foundation.ExperimentalFoundationApi")
        optIn("kotlinx.coroutines.DelicateCoroutinesApi")
        optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
//        optIn("org.orbitmvi.orbit.annotation.OrbitExperimental")
        optIn("androidx.compose.foundation.layout.ExperimentalLayoutApi")
        optIn("androidx.compose.ui.text.ExperimentalTextApi")
      }
    }
    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)
      implementation(libs.voyager.navigator)
      implementation(libs.voyager.screenModel)
      implementation(libs.voyager.bottomSheetNavigator)
      implementation(libs.voyager.tabNavigator)
      implementation(libs.voyager.transitions)
      implementation(libs.voyager.koin)
      implementation(libs.kermit)
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.composeIcons.featherIcons)
      implementation(libs.kotlinx.serialization.json)
      implementation(libs.kotlinx.datetime)
      implementation(libs.multiplatformSettings)
      implementation(libs.multiplatformSettings.coroutines)
      implementation(libs.koin.core)
    }

    commonTest.dependencies {
      implementation(kotlin("test"))
      @OptIn(ExperimentalComposeLibrary::class)
      implementation(compose.uiTest)
      implementation(libs.kotlinx.coroutines.test)
    }

    androidMain.dependencies {
      implementation(compose.uiTooling)
      implementation(libs.androidx.activityCompose)
      implementation(libs.kotlinx.coroutines.android)
      //noinspection UseTomlInstead
      implementation("net.java.dev.jna:jna:5.14.0@aar")
    }

    jvmMain.dependencies {
      implementation(compose.desktop.currentOs)
      implementation(libs.kotlinx.coroutines.swing)
    }

    iosMain.dependencies {
    }

  }
}

android {
  namespace = "dev.darkokoa.fubukidaze"
  compileSdk = 34

  defaultConfig {
    minSdk = 28
    targetSdk = 34

    applicationId = "dev.darkokoa.fubukidaze.androidApp"
    versionCode = 1
    versionName = "24.04.18"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  sourceSets["main"].apply {
    manifest.srcFile("src/androidMain/AndroidManifest.xml")
    res.srcDirs("src/androidMain/res")
  }
  //https://developer.android.com/studio/test/gradle-managed-devices
  @Suppress("UnstableApiUsage")
  testOptions {
    managedDevices.devices {
      maybeCreate<ManagedVirtualDevice>("pixel5").apply {
        device = "Pixel 5"
        apiLevel = 34
        systemImageSource = "aosp"
      }
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.11"
  }
}

compose.desktop {
  application {
    mainClass = "MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "dev.darkokoa.fubukidaze.desktopApp"
      packageVersion = "24.04.18"
    }
  }
}

buildConfig {
  // BuildConfig configuration here.
  // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.protobuf)
    id("maven-publish")
}

// 读取 git 的提交数量作为版本号
val gitVersionCode by lazy {
    val stdout = org.apache.commons.io.output.ByteArrayOutputStream()
    rootProject.exec {
        val cmd = "git rev-list HEAD --first-parent --count".split(" ")
        commandLine(cmd)
        standardOutput = stdout
    }
    stdout.toString().trim().toInt()
}

// 读取 git 的 commit tag 作为应用的版本名，如果后面
val gitVersionTag by lazy {
    val stdout = org.apache.commons.io.output.ByteArrayOutputStream()
    rootProject.exec {
        val cmd = "git rev-list HEAD --first-parent --count".split(" ")
        commandLine(cmd)
        standardOutput = stdout
    }
    stdout.toString().trim()
}

android {
    namespace = "com.zhangls.base"
    compileSdk = libs.versions.compileSdk.get().toInt()
    buildToolsVersion = libs.versions.buildTool.get()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        consumerProguardFiles("proguard-rules.pro")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    kotlin {
        jvmToolchain(11)
    }
    lint {
        disable.add("SpUsage")
        disable.add("RtlHardcoded")
    }
    buildFeatures {
        buildConfig = true
    }
    packaging {
        jniLibs {
            pickFirsts.add("lib/arm64-v8a/libc++_shared.so")
            pickFirsts.add("lib/armeabi-v7a/libc++_shared.so")
            excludes.add("**/*.proto")
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    // 测试、Debug
    testImplementation(libs.junit)
    androidTestImplementation(libs.extJunit)
    androidTestImplementation(libs.espresso)

    api(libs.coroutinesAndroid)
    api(libs.coreKtx)
    api(libs.materialDesign)
    api(libs.constraintLayout)
    api(libs.navigationFragmentKtx)
    api(libs.datastorePreferences)

    // 网络请求框架：Retrofit + Gson
    api(libs.gson)
    api(libs.retrofit2)
    api(libs.retrofit2ConverterGson)
    api(libs.okHttpLogging)
    api(libs.utilcodex)
    // 日志框架
    api(libs.glog)
    api(libs.timber)
    api(libs.protobuf)
}

afterEvaluate {
    publishing {
        publications {
            // Creates a Maven publication called "release".
            create<MavenPublication>("product") {
                // Applies the component for the release build variant.
                from(components["release"])

                groupId = "com.zhangls"
                artifactId = "base"
                version = gitVersionTag
            }
        }
    }
}

protobuf {
    protoc {
        // 配置 protoc 编译器
        artifact = "com.google.protobuf:protoc:3.21.12"
    }
    plugins {
        create("javalite") {
            artifact = "com.google.protobuf:protoc-gen-javalite:3.0.0"
        }
    }
    // 配置生成目录，编译后会在 build 的目录下生成对应的 java 文件
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("javalite")
            }
        }
    }
}
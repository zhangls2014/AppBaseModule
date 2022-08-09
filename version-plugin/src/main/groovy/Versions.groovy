/**
 * 版本信息
 */
interface Versions {
    def buildTool = '32.0.0'
    def minSdk = 23
    def targetSdk = 31
    def compileSdk = 31
}

/**
 * 依赖库路径
 */
interface Deps {
    /*
     * test
     */
    //junit
    String junit = 'junit:junit:4.13.2'
    //runner
    String runner = 'androidx.test:runner:1.4.0'
    //extJunit
    String extJunit = 'androidx.test.ext:junit:1.1.3'
    //espresso
    String espresso = 'androidx.test.espresso:espresso-core:3.4.0'

    /*
     * androidx
     */
    //appcompat
    String appcompat = 'androidx.appcompat:appcompat:1.4.2'
    //fragment
    String fragment = 'androidx.fragment:fragment-ktx:1.5.1'
    //activity
    String activity = 'androidx.activity:activity:1.5.1'
    //andAnnotations
    String andAnnotations = 'androidx.annotation:annotation:1.3.0'
    //materialDesign
    String materialDesign = 'com.google.android.material:material:1.5.0'
    //cardView
    String cardView = 'androidx.cardview:cardview:1.0.0'
    //recyclerView
    String recyclerView = 'androidx.recyclerview:recyclerview:1.2.1'
    //transition
    String transition = 'androidx.transition:transition:1.4.1'
    //constraintLayout
    String constraintLayout = 'androidx.constraintlayout:constraintlayout:2.1.0'
    //coreKtx
    String coreKtx = 'androidx.core:core-ktx:1.7.0'
    //preference
    String preference = 'androidx.preference:preference:1.2.0'
    //viewpager2
    String viewpager2 = 'androidx.viewpager2:viewpager2:1.0.0'

    /*
     * lifecycle
     */
    //lifecycleRuntime
    String lifecycleRuntime = 'androidx.lifecycle:lifecycle-runtime-ktx:2.5.1'
    //viewModel
    String viewModel = 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1'

    /*
     * cameraX
     */
    //cameraXCore
    String cameraXCore = "androidx.camera:camera-core:1.1.0"
    //cameraXCamera2
    String cameraXCamera2 = "androidx.camera:camera-camera2:1.1.0"
    //cameraXLifecycle
    String cameraXLifecycle = "androidx.camera:camera-lifecycle:1.1.0"
    //cameraXView
    String cameraXView = 'androidx.camera:camera-view:1.1.0'
    //cameraXExtensions
    String cameraXExtensions = "androidx.camera:camera-extensions:1.1.0"

    /**
     * navigation
     */
    //navigationFragmentKtx
    String navigationFragmentKtx = 'androidx.navigation:navigation-fragment-ktx:2.5.1'
    //navigationUI
    String navigationUI = "androidx.navigation:navigation-ui:2.5.1"

    //flowEventBus : https://github.com/biubiuqiu0/flow-event-bus
    String flowEventBus = 'com.github.biubiuqiu0:flow-event-bus:0.0.2'

    //agentWeb
    String agentWeb = 'com.github.Justson.AgentWeb:agentweb-core:v5.0.0-alpha.1-androidx'
    //agentWebFileChooser
    String agentWebFileChooser = 'com.github.Justson.AgentWeb:agentweb-filechooser:v5.0.0-alpha.1-androidx'

    /*
     * google
     */
    //pagingRuntime
    String pagingRuntime = 'androidx.paging:paging-runtime-ktx:3.1.0'

    //flexBox : https://github.com/google/flexbox-layout
    String flexBox = 'com.google.android:flexbox:2.0.0'
    //gson : https://github.com/google/gson
    String gson = 'com.google.code.gson:gson:2.9.0'

    //coroutinesAndroid
    String coroutinesAndroid = 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1'

    /*
     * 模块化框架
     */
    //appJoint : https://github.com/PrototypeZ/AppJoint/blob/master/README_zh.md
    String appJoint = 'io.github.prototypez:app-joint:1.7'
    //appJointCore
    String appJointCore = 'io.github.prototypez:app-joint-core:1.7'

    /*
     * retrofit2
     */
    //retrofit2 : https://github.com/square/retrofit
    String retrofit2 = 'com.squareup.retrofit2:retrofit:2.9.0'
    //retrofit2ConverterGson
    String retrofit2ConverterGson = 'com.squareup.retrofit2:converter-gson:2.9.0'
    //okHttp
    String okHttp = 'com.squareup.okhttp3:okhttp:4.9.3'
    //okHttpLogging
    String okHttpLogging = 'com.squareup.okhttp3:logging-interceptor:4.9.3'
    //okio
    String okio = 'com.squareup.okio:okio:2.10.0'

    //multiType
    String multiType = 'com.drakeet.multitype:multitype:4.3.0'
    //swipeRefreshLayout
    String swipeRefreshLayout = 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'

    //slf4j
    String slf4j = 'org.slf4j:slf4j-api:1.7.32'

    //mavericks : https://github.com/airbnb/mavericks
    String mavericks = 'com.airbnb.android:mavericks:2.5.1'
    //mavericksNavigation
    String mavericksNavigation = 'com.airbnb.android:mavericks-navigation:2.5.1'

    //convex : https://github.com/ParadiseHell/convex
    String convex = "org.paradisehell.convex:convex:1.0.0"

    /**
     * room
     */
    //roomRuntime
    String roomRuntime = 'androidx.room:room-runtime:2.4.2'
    //roomCompiler
    String roomCompiler = 'androidx.room:room-compiler:2.4.2'
    //roomKtx : Kotlin Extensions and Coroutines support for Room
    String roomKtx = 'androidx.room:room-ktx:2.4.2'
    //roomPaging
    String roomPaging = 'androidx.room:room-paging:2.4.2'

    //tencentIM
    String tencentIM = 'com.tencent.imsdk:imsdk:5.1.66'

    /*
     * 路由
     */
    //aRouterApi : https://github.com/alibaba/ARouter
    String aRouterApi = 'com.alibaba:arouter-api:1.5.0'
    //aRouterCompiler
    String aRouterCompiler = 'com.alibaba:arouter-compiler:1.2.2'

    /*
     * 图片加载
     */
    //coil : https://github.com/coil-kt/coil
    String coil = 'io.coil-kt:coil:1.4.0'
    //coilBase
    String coilBase = 'io.coil-kt:coil-base:1.4.0'
    //coilGif
    String coilGif = 'io.coil-kt:coil-gif:1.4.0'
    //coilSVG
    String coilSVG = 'io.coil-kt:coil-svg:1.4.0'

    //XXPermissions : https://github.com/getActivity/XXPermissions
    String XXPermissions = 'com.github.getActivity:XXPermissions:13.6'

    /*
     * bug反馈
     */
    //bugly
    String bugly = 'com.tencent.bugly:crashreport:4.0.4'
    //buglyNDK
    String buglyNDK = 'com.tencent.bugly:nativecrashreport:3.9.2'

    /**
     * Web 浏览器
     */
    //tencentX5 : https://x5.tencent.com/docs/access.html
    String tencentX5 = 'com.tencent.tbs:tbssdk:44181'

    //weChatOpenSDK : https://developers.weixin.qq.com/doc/oplatform/Downloads/Android_Resource.html
    String weChatOpenSDK = 'com.tencent.mm.opensdk:wechat-sdk-android-without-mta:6.8.0'

    //ZXingLite : https://github.com/jenly1314/ZXingLite
    String ZXingLite = 'com.github.jenly1314:zxing-lite:2.1.1'

    //MLKitCore : https://github.com/jenly1314/MLKit，Camera核心 (*必须项)
    String MLKitCore = 'com.github.jenly1314.MLKit:mlkit-camera-core:1.0.3'

    //MLKitBarcode : https://github.com/jenly1314/MLKit，条码识别 (可选项)
    String MLKitBarcode = 'com.github.jenly1314.MLKit:mlkit-barcode-scanning:1.0.3'

    //pdfViewer  : https://github.com/barteksc/AndroidPdfViewer
    String pdfViewer = 'com.github.barteksc:android-pdf-viewer:3.1.0-beta.1'
    //pdfium
    String pdfium = 'com.github.barteksc:pdfium-android:1.9.0'

    //exoPlayer: https://github.com/google/ExoPlayer
    String exoPlayer = 'com.google.android.exoplayer:exoplayer:2.16.1'
    //exoCore: https://github.com/google/ExoPlayer
    String exoCore = 'com.google.android.exoplayer:exoplayer-core:2.16.1'
    //exoUI: https://github.com/google/ExoPlayer
    String exoUI = 'com.google.android.exoplayer:exoplayer-ui:2.16.1'

    /*
     * 自定义view
     */
    //discreteScrollview : https://github.com/yarolegovich/DiscreteScrollView
    String discreteScrollview = 'com.yarolegovich:discrete-scrollview:1.4.9'
    //easySwipeMenuLayout : https://github.com/anzaizai/EasySwipeMenuLayout
    String easySwipeMenuLayout = 'com.github.anzaizai:EasySwipeMenuLayout:1.1.4'
    //swipeDelMenuLayout :侧滑删除 https://github.com/mcxtzhang/SwipeDelMenuLayout/blob/master/README-cn.md
    String swipeDelMenuLayout = 'com.github.mcxtzhang:SwipeDelMenuLayout:V1.3.0'
    //swipeBack
    String swipeBack = 'n.simonlee.widget:swipeback:1.0.15'
    //banner : https://github.com/youth5201314/banner
    String banner = 'io.github.youth5201314:banner:2.2.2'
    //photoView : https://github.com/chrisbanes/PhotoViewƒ
    String photoView = 'com.github.chrisbanes:PhotoView:2.3.0'
    //androidPickerView : 时间,地址选择器 https://github.com/Bigkoo/Android-PickerView
    String androidPickerView = 'com.contrarywind:Android-PickerView:4.1.9'
    //switchButton : https://github.com/kyleduo/SwitchButton
    String switchButton = 'com.kyleduo.switchbutton:library:2.1.0'

    //tencentTRTC
    String tencentTRTC = 'com.tencent.liteav:LiteAVSDK_TRTC:9.5.11347'

    //eventBus : https://github.com/greenrobot/EventBus
    String eventBus = 'org.greenrobot:eventbus:3.3.1'

    /*
     * 权限
     */
    //andPermission : https://github.com/yanzhenjie/AndPermission
    String andPermission = 'com.yanzhenjie:permission:2.0.3'

    //mmkv : 基于 mmap 的高性能通用 key-value 组件 https://github.com/Tencent/MMKV/blob/master/readme_cn.md
    String mmkv = 'com.tencent:mmkv-static:1.2.8'

    //betterLinkMovementMethod : 自定义 LinkMovementMethod，支持点击和长按操作
    String betterLinkMovementMethod = 'me.saket:better-link-movement-method:2.2.0'

    //AutoSize : https://github.com/JessYanCoding/AndroidAutoSize
    String AutoSize = 'me.jessyan:autosize:1.2.0'

    //FitAndroid8 : Android 7/8 FileProvider适配 https://github.com/steven2947/FitAndroid8
    String FitAndroid8 = 'com.github.steven2947:FitAndroid8:0.5.0'

    //utilCode
    String utilCode = 'com.blankj:utilcodex:1.31.0'

    /*
    * 测试工具
    */
    //leakCanaryAndroid : https://github.com/square/leakcanary
    String leakCanaryAndroid = 'com.squareup.leakcanary:leakcanary-android:2.7'

    //blockCanaryAndroid :卡顿监控和提示 https://github.com/markzhai/AndroidPerformanceMonitor
    String blockCanaryAndroid = 'com.github.markzhai:blockcanary-android:1.5.0'

    //timber
    String timber = 'com.jakewharton.timber:timber:4.7.1'

    //baseUtils : https://github.com/zhangls2014/BaseUtils
    String baseUtils = 'com.github.zhangls2014:BaseUtils:0.1.6'

    //jsBridge : https://github.com/zhangls2014/JsBridge
    String jsBridge = 'com.github.zhangls2014:JSBridge:1.1'

    //huaweiFaceShapePointModel
    String huaweiFaceShapePointModel = 'com.huawei.hms:ml-computer-vision-face-shape-point-model:2.0.5.300'
    //huaweiFace
    String huaweiFace = 'com.huawei.hms:ml-computer-vision-face:2.0.5.300'

    //deviceName : https://github.com/jaredrummler/AndroidDeviceNames
    String deviceName = 'com.jaredrummler:android-device-names:2.1.0'
}

package com.zhangls.base.extension

import android.view.View
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * @author zhangls
 */

/**
 * WindowInsetsControllerCompat 不能正确的兼容各种系统版本，所以还是使用该方法实现全屏代码
 */
@Suppress("DEPRECATION")
fun Window.hideSystemBars() {
    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_FULLSCREEN
}

/**
 * WindowInsetsControllerCompat 不能正确的兼容各种系统版本，所以还是使用该方法实现退出全屏代码
 */
@Suppress("DEPRECATION")
fun Window.showSystemBars() {
    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_VISIBLE
}

fun Window.lightStatusBars(view: View, isLightStatusBars: Boolean) {
    WindowInsetsControllerCompat(this, view).isAppearanceLightStatusBars = isLightStatusBars
}
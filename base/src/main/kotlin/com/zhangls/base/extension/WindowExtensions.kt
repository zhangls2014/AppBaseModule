package com.zhangls.base.extension

import android.view.View
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * @author zhangls
 */

fun Window.hideSystemBars(view: View) {
    WindowCompat.setDecorFitsSystemWindows(this, false)

    WindowInsetsControllerCompat(this, view).let {
        it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        it.hide(WindowInsetsCompat.Type.systemBars())
    }
}

fun Window.showSystemBars(view: View) {
    WindowCompat.setDecorFitsSystemWindows(this, true)

    WindowInsetsControllerCompat(this, view).show(WindowInsetsCompat.Type.systemBars())
}

fun Window.lightStatusBars(view: View, isLightStatusBars: Boolean) {
    WindowInsetsControllerCompat(this, view).isAppearanceLightStatusBars = isLightStatusBars
}
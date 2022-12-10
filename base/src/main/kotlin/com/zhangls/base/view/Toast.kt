package com.zhangls.base.view

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.Gravity
import com.blankj.utilcode.util.ToastUtils
import com.zhangls.base.R


/**
 * @author zhangls
 */
class Toast {
    companion object {
        @JvmStatic
        fun showToast(
            content: String,
            leftIcon: Drawable? = null,
            gravity: Int = Gravity.CENTER,
            xOffset: Int = 0,
            yOffset: Int = 0
        ) {
            ToastUtils.make()
                .setTextSize(16)
                .setTextColor(Color.WHITE)
                .setBgResource(R.drawable.base_shape_bg_toast)
                .setDurationIsLong(false)
                .setLeftIcon(leftIcon)
                .setGravity(gravity, xOffset, yOffset)
                .show(content)
        }

        @JvmStatic
        fun longToast(
            content: String,
            leftIcon: Drawable? = null,
            gravity: Int = Gravity.CENTER,
            xOffset: Int = 0,
            yOffset: Int = 0
        ) {
            ToastUtils.make()
                .setTextSize(16)
                .setTextColor(Color.WHITE)
                .setBgResource(R.drawable.base_shape_bg_toast)
                .setDurationIsLong(true)
                .setLeftIcon(leftIcon)
                .setGravity(gravity, xOffset, yOffset)
                .show(content)
        }

        @JvmStatic
        fun showToast(
            resId: Int,
            leftIcon: Drawable? = null,
            gravity: Int = Gravity.CENTER,
            xOffset: Int = 0,
            yOffset: Int = 0
        ) {
            ToastUtils.make()
                .setTextSize(16)
                .setTextColor(Color.WHITE)
                .setBgResource(R.drawable.base_shape_bg_toast)
                .setDurationIsLong(false)
                .setLeftIcon(leftIcon)
                .setGravity(gravity, xOffset, yOffset)
                .show(resId)
        }

        @JvmStatic
        fun longToast(
            resId: Int,
            leftIcon: Drawable? = null,
            gravity: Int = Gravity.CENTER,
            xOffset: Int = 0,
            yOffset: Int = 0
        ) {
            ToastUtils.make()
                .setTextSize(16)
                .setTextColor(Color.WHITE)
                .setBgResource(R.drawable.base_shape_bg_toast)
                .setDurationIsLong(true)
                .setLeftIcon(leftIcon)
                .setGravity(gravity, xOffset, yOffset)
                .show(resId)
        }

        @JvmStatic
        fun cancel() {
            ToastUtils.cancel()
        }
    }
}
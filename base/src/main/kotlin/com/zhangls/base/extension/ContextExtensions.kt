@file:Suppress("unused")

package com.zhangls.base.extension

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat

/**
 * 通过 res 获取颜色值
 */
@ColorInt
fun Context.colorInt(@ColorRes id: Int): Int = ContextCompat.getColor(this, id)

/**
 * 通过 res 获取颜色值
 */
fun Context.colorStateListCompat(@ColorRes id: Int): ColorStateList? =
    ContextCompat.getColorStateList(this, id)

/**
 * 通过 res 获取颜色值
 */
fun Context.colorStateListFromAttr(
    @AttrRes attrColor: Int, typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): ColorStateList? {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return ContextCompat.getColorStateList(this, typedValue.resourceId)
}

/**
 * 通过属性获取颜色值
 */
@ColorInt
fun Context.colorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

/**
 * 通过属性获取尺寸值
 */
fun Context.dimensionFromAttr(
    @AttrRes attrDimen: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrDimen, typedValue, resolveRefs)
    return resources.getDimensionPixelSize(typedValue.resourceId)
}

/**
 * 通过属性获取 StyleRes
 */
fun Context.styleFromAttr(
    @AttrRes attrStyle: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): ContextThemeWrapper {
    theme.resolveAttribute(attrStyle, typedValue, resolveRefs)
    return ContextThemeWrapper(this, typedValue.resourceId)
}

/**
 * 通过属性获取 ResourceRes
 */
fun Context.resourceIdFromAttr(
    @AttrRes attrStyle: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrStyle, typedValue, resolveRefs)
    return typedValue.resourceId
}

/**
 * 获取 Drawable
 */
fun Context.drawableInt(@DrawableRes res: Int): Drawable? = ContextCompat.getDrawable(this, res)
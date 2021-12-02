@file:Suppress("unused")

package com.zhangls.base.extension

import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.contains
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import com.zhangls.base.R


/**
 * @author zhangls
 */

/**
 * 通过扩展方法，实现拦截按钮连续点击事件的功能
 *
 * 源于 QMUI。
 */
fun View.onClick(wait: Long = 200, block: ((View) -> Unit)) {
    setOnClickListener {
        val current = SystemClock.uptimeMillis()
        val lastClickTime = (it.getTag(R.id.base_click_timestamp) as? Long) ?: 0
        if (current - lastClickTime > wait) {
            it.setTag(R.id.base_click_timestamp, current)
            block(it)
        }
    }
}


val View.wrapContent: Int
    get() = ViewGroup.LayoutParams.WRAP_CONTENT

val View.matchParent: Int
    get() = ViewGroup.LayoutParams.MATCH_PARENT

val ViewGroup.wrapContent: Int
    get() = ViewGroup.LayoutParams.WRAP_CONTENT

val ViewGroup.matchParent: Int
    get() = ViewGroup.LayoutParams.MATCH_PARENT

val View.matchConstraint: Int
    get() = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT

val ConstraintLayout.LayoutParams.parentId: Int
    get() = ConstraintLayout.LayoutParams.PARENT_ID

/**
 * 添加 View，如果没有被添加
 */
fun ViewGroup.addViewNotContains(view: View, index: Int = -1) {
    if (contains(view).not()) {
        addView(view, index)
    }
}

fun View.snackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun View.snackBar(@StringRes message: Int) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun View.longSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun View.longSnackBar(@StringRes message: Int) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

/**
 * 配置 LayoutParams 的扩展内联函数
 */
fun ViewGroup.coordinatorLayoutParams(
    width: Int,
    height: Int,
    params: (CoordinatorLayout.LayoutParams.() -> Unit)? = null
): CoordinatorLayout.LayoutParams {
    val layoutParams = CoordinatorLayout.LayoutParams(width, height)
    params?.let { it(layoutParams) }
    return layoutParams
}

fun View.collapsingToolbarLayoutParams(
    width: Int,
    height: Int,
    params: (CollapsingToolbarLayout.LayoutParams.() -> Unit)? = null
): CollapsingToolbarLayout.LayoutParams {
    val layoutParams = CollapsingToolbarLayout.LayoutParams(width, height)
    params?.let { it(layoutParams) }
    return layoutParams
}

fun View.constraintLayoutParams(
    width: Int,
    height: Int,
    params: (ConstraintLayout.LayoutParams.() -> Unit)? = null
): ConstraintLayout.LayoutParams {
    val layoutParams = ConstraintLayout.LayoutParams(width, height)
    params?.let { it(layoutParams) }
    return layoutParams
}

fun View.textInputLayoutParams(
    width: Int,
    height: Int,
    params: (LinearLayout.LayoutParams.() -> Unit)? = null
): LinearLayout.LayoutParams {
    val layoutParams = LinearLayout.LayoutParams(width, height)
    params?.let { it(layoutParams) }
    return layoutParams
}

fun View.drawerLayoutParams(
    width: Int,
    height: Int,
    params: (DrawerLayout.LayoutParams.() -> Unit)? = null
): DrawerLayout.LayoutParams {
    val layoutParams = DrawerLayout.LayoutParams(width, height)
    params?.let { it(layoutParams) }
    return layoutParams
}

fun View.appBarLayoutParams(
    width: Int,
    height: Int,
    params: (AppBarLayout.LayoutParams.() -> Unit)? = null
): AppBarLayout.LayoutParams {
    val layoutParams = AppBarLayout.LayoutParams(width, height)
    params?.let { it(layoutParams) }
    return layoutParams
}

fun View.linearLayoutParams(
    width: Int,
    height: Int,
    params: (LinearLayout.LayoutParams.() -> Unit)? = null
): LinearLayout.LayoutParams {
    val layoutParams = LinearLayout.LayoutParams(width, height)
    params?.let { it(layoutParams) }
    return layoutParams
}

fun View.frameLayoutParams(
    width: Int,
    height: Int,
    params: (FrameLayout.LayoutParams.() -> Unit)? = null
): FrameLayout.LayoutParams {
    val layoutParams = FrameLayout.LayoutParams(width, height)
    params?.let { it(layoutParams) }
    return layoutParams
}

fun View.toolbarParams(
    width: Int,
    height: Int,
    params: (Toolbar.LayoutParams.() -> Unit)? = null
): Toolbar.LayoutParams {
    val layoutParams = Toolbar.LayoutParams(width, height)
    params?.let { it(layoutParams) }
    return layoutParams
}

fun View.viewGroupParams(
    width: Int,
    height: Int,
    params: (ViewGroup.LayoutParams.() -> Unit)? = null
): ViewGroup.LayoutParams {
    val layoutParams = ViewGroup.LayoutParams(width, height)
    params?.let { it(layoutParams) }
    return layoutParams
}

fun View.recyclerViewParams(
    width: Int,
    height: Int,
    params: (RecyclerView.LayoutParams.() -> Unit)? = null
): RecyclerView.LayoutParams {
    val layoutParams = RecyclerView.LayoutParams(width, height)
    params?.let { it(layoutParams) }
    return layoutParams
}
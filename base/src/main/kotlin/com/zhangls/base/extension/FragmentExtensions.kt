@file:Suppress("unused")

package com.zhangls.base.extension

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


/**
 * Fragment 扩展
 *
 * @author zhangls
 */
fun Fragment.showSnackBar(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.showSnackBar(@StringRes resId: Int) {
    Snackbar.make(requireView(), resId, Snackbar.LENGTH_SHORT).show()
}
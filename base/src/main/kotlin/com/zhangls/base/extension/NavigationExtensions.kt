package com.zhangls.base.extension

import androidx.navigation.NavController
import androidx.navigation.NavDirections

/**
 * Navigation 扩展方法
 *
 * @author zhangls
 */

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}
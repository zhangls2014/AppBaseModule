package com.zhangls.base.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import androidx.lifecycle.withResumed
import androidx.lifecycle.withStarted
import kotlinx.coroutines.launch


/**
 * @author zhangls
 */
fun LifecycleOwner.launchWhenCreated(block: () -> Unit) {
    lifecycleScope.launch { lifecycle.withCreated(block) }
}

fun LifecycleOwner.launchWhenStarted(block: () -> Unit) {
    lifecycleScope.launch { lifecycle.withStarted(block) }
}

fun LifecycleOwner.launchWhenResumed(block: () -> Unit) {
    lifecycleScope.launch { lifecycle.withResumed(block) }
}
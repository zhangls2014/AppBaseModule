package com.zhangls.base.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


/**
 * @author zhangls
 */
fun LifecycleOwner.launchWhenCreated(block: suspend () -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            block()
            cancel()
        }
    }
}

fun LifecycleOwner.launchWhenStarted(block: suspend () -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
            cancel()
        }
    }
}

fun LifecycleOwner.launchWhenResumed(block: suspend () -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            block()
            cancel()
        }
    }
}
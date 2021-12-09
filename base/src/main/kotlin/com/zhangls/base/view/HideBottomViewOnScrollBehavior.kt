package com.zhangls.base.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewPropertyAnimator
import com.google.android.material.animation.AnimationUtils
import java.lang.ref.WeakReference

/**
 * @author zhangls
 */
class HideBottomViewOnScrollBehavior(child: View) {
    private var height = 0
    private var oldOffset = 0
    private var currentState = STATE_SCROLLED_UP
    private var currentAnimator: ViewPropertyAnimator? = null
    private val childView = WeakReference(child)
    private val paramsCompat = childView.get()?.layoutParams as? MarginLayoutParams


    fun onVerticalScroll(verticalOffset: Int) {
        val measuredHeight =  childView.get()?.measuredHeight ?: 0
        val bottomMargin = paramsCompat?.bottomMargin ?: 0
        height = measuredHeight + bottomMargin

        if (oldOffset > verticalOffset) {
            slideDown()
        } else {
            slideUp()
        }

        oldOffset = verticalOffset
    }

    private fun slideUp() {
        if (currentState == STATE_SCROLLED_UP) {
            return
        }
        if (currentAnimator != null) {
            currentAnimator!!.cancel()
            childView.get()?.clearAnimation()
        }
        currentState = STATE_SCROLLED_UP
        childView.get()?.let {
            animateChildTo(
                it,
                0,
                ENTER_ANIMATION_DURATION.toLong(),
                AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR
            )
        }
    }

    private fun slideDown() {
        if (currentState == STATE_SCROLLED_DOWN) {
            return
        }
        if (currentAnimator != null) {
            currentAnimator!!.cancel()
            childView.get()?.clearAnimation()
        }
        currentState = STATE_SCROLLED_DOWN
        childView.get()?.let {
            animateChildTo(
                it,
                height,
                EXIT_ANIMATION_DURATION.toLong(),
                AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR
            )
        }
    }

    private fun animateChildTo(child: View, targetY: Int, duration: Long, interpolator: TimeInterpolator) {
        currentAnimator = child
            .animate()
            .translationY(targetY.toFloat())
            .setInterpolator(interpolator)
            .setDuration(duration)
            .setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        currentAnimator = null
                    }
                })
    }

    companion object {
        private const val ENTER_ANIMATION_DURATION = 225
        private const val EXIT_ANIMATION_DURATION = 175
        private const val STATE_SCROLLED_DOWN = 1
        private const val STATE_SCROLLED_UP = 2
    }
}
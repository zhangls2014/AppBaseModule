package com.zhangls.base.view

import android.content.Context
import android.graphics.Color
import android.graphics.Outline
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.annotation.UiThread
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.CHAIN_PACKED
import com.google.android.material.textview.MaterialTextView
import com.zhangls.base.R
import com.zhangls.base.extension.*

/**
 * 加载中
 *
 * @author zhangls
 */
class LoadingView : FrameLayout {
    // These fields should only be accessed on the UI thread.
    private var mStartTime: Long = -1
    private var mPostedHide = false
    private var mPostedShow = false
    private var mDismissed = false

    private val mDelayedHide = Runnable {
        mPostedHide = false
        mStartTime = -1
        visibility = GONE
    }

    private val mDelayedShow = Runnable {
        mPostedShow = false
        if (!mDismissed) {
            mStartTime = System.currentTimeMillis()
            visibility = VISIBLE
        }
    }

    private val box by lazy {
        ConstraintLayout(context).apply {
            setBackgroundColor(Color.parseColor("#CC000000"))
            setPadding(24.dp, 16.dp, 24.dp, 16.dp)

            clipToOutline = true
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    view?.let { outline?.setRoundRect(0, 0, it.width, it.height, 4.dpFloat) }
                }
            }
            layoutParams = frameLayoutParams(wrapContent, wrapContent) {
                gravity = Gravity.CENTER
            }

            addView(progressBar)
            addView(text)
        }
    }

    private val progressBar by lazy {
        ProgressBar(context, null, 0, R.style.Widget_AppCompat_ProgressBar).apply {
            id = R.id.progressBar
            isIndeterminate = true
            layoutParams = constraintLayoutParams(wrapContent, wrapContent) {
                topToTop = parentId
                bottomToBottom = parentId
                leftToLeft = parentId
                rightToLeft = R.id.tvText

                horizontalChainStyle = CHAIN_PACKED
            }
        }
    }

    private val text by lazy {
        MaterialTextView(context).apply {
            id = R.id.tvText
            textSize = 12F
            text = "加载中…"
            setTextColor(context.colorInt(android.R.color.white))
            layoutParams = constraintLayoutParams(wrapContent, wrapContent) {
                topToTop = R.id.progressBar
                bottomToBottom = R.id.progressBar
                leftToRight = R.id.progressBar
                rightToRight = parentId

                leftMargin = 16.dp
            }
        }
    }


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        addView(box)
        isClickable = true
    }

    fun setText(@StringRes content: Int) {
        text.setText(content)
    }

    fun setText(content: String) {
        text.text = content
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        removeCallbacks()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeCallbacks()
    }

    private fun removeCallbacks() {
        removeCallbacks(mDelayedHide)
        removeCallbacks(mDelayedShow)
    }

    /**
     * Hide the progress view if it is visible. The progress view will not be
     * hidden until it has been shown for at least a minimum show time. If the
     * progress view was not yet visible, cancels showing the progress view.
     *
     *
     * This method may be called off the UI thread.
     */
    fun hide() {
        // This method used to be synchronized, presumably so that it could be safely called off
        // the UI thread; however, the referenced fields were still accessed both on and off the
        // UI thread, e.g. not thread-safe. Now we hand-off everything to the UI thread.
        post { hideOnUiThread() }
    }

    @UiThread
    private fun hideOnUiThread() {
        mDismissed = true
        removeCallbacks(mDelayedShow)
        mPostedShow = false
        val diff = System.currentTimeMillis() - mStartTime
        if (diff >= MIN_SHOW_TIME_MS || mStartTime == -1L) {
            // The progress spinner has been shown long enough
            // OR was not shown yet. If it wasn't shown yet,
            // it will just never be shown.
            visibility = GONE
        } else {
            // The progress spinner is shown, but not long enough,
            // so put a delayed message in to hide it when its been
            // shown long enough.
            if (!mPostedHide) {
                postDelayed(mDelayedHide, MIN_SHOW_TIME_MS - diff)
                mPostedHide = true
            }
        }
    }

    /**
     * Show the progress view after waiting for a minimum delay. If
     * during that time, hide() is called, the view is never made visible.
     *
     *
     * This method may be called off the UI thread.
     */
    fun show() {
        // This method used to be synchronized, presumably so that it could be safely called off
        // the UI thread; however, the referenced fields were still accessed both on and off the
        // UI thread, e.g. not thread-safe. Now we hand-off everything to the UI thread.
        post { showOnUiThread() }
    }

    @UiThread
    private fun showOnUiThread() {
        // Reset the start time.
        mStartTime = -1
        mDismissed = false
        removeCallbacks(mDelayedHide)
        mPostedHide = false
        if (!mPostedShow) {
            postDelayed(mDelayedShow, MIN_DELAY_MS.toLong())
            mPostedShow = true
        }
    }

    companion object {
        private const val MIN_SHOW_TIME_MS = 500
        private const val MIN_DELAY_MS = 500
    }
}
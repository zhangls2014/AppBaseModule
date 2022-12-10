package com.zhangls.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.zhangls.base.extension.addViewNotContains
import com.zhangls.base.extension.coordinatorLayoutParams
import com.zhangls.base.extension.dpFloat
import com.zhangls.base.extension.wrapContent


/**
 * 给子 view 提供加载框
 *
 * @author zhangls
 */
open class FragmentLoadingView : CoordinatorLayout {

    private val loadingView by lazy {
        LoadingView(context).apply {
            visibility = View.GONE
            layoutParams = coordinatorLayoutParams(wrapContent, wrapContent) {
                gravity = Gravity.CENTER
                translationZ = 1.dpFloat
            }
        }
    }


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    fun loading(visible: Boolean, text: String = "加载中") {
        if (visible) {
            loadingView.setText(text)
            addViewNotContains(loadingView)
            loadingView.bringToFront()
            loadingView.show()
        } else {
            loadingView.hide()
        }
    }

}
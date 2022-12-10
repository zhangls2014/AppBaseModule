package com.zhangls.base.view

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.StringRes
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.zhangls.base.R
import com.zhangls.base.extension.*

/**
 * 标题栏，文字居中显示
 *
 * @author zhangls
 */
class FragmentTitleView : AppBarLayout {

    val toolbar by lazy {
        MaterialToolbar(context).apply {
            id = R.id.toolbar
            title = ""
            setNavigationIcon(R.drawable.base_ic_navigate_before)

            layoutParams = appBarLayoutParams(matchParent, context.dimensionFromAttr(R.attr.actionBarSize)) {
                scrollFlags = LayoutParams.SCROLL_FLAG_NO_SCROLL
            }
        }
    }


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        layoutParams = coordinatorLayoutParams(matchParent, wrapContent)
        setBackgroundResource(R.color.base_white)

        addView(toolbar)
    }


    fun setCenterTitle(title: String) {
        toolbar.isTitleCentered = true
        toolbar.title = title
    }

    fun setCenterTitle(@StringRes titleRes: Int) {
        toolbar.isTitleCentered = true
        toolbar.setTitle(titleRes)
    }
}
package com.zhangls.base.view.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Outline
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewOutlineProvider
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.zhangls.base.R
import com.zhangls.base.extension.*

/**
 * @author zhangls
 */
class SimpleView : ConstraintLayout {

    val title by lazy {
        MaterialTextView(context).apply {
            id = R.id.tvTitle
            textSize = 16F
            gravity = Gravity.CENTER
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
            setTypeface(typeface, Typeface.BOLD)
            setTextColor(context.colorInt(R.color.base_black_title))

            layoutParams = constraintLayoutParams(matchConstraint, wrapContent) {
                topToTop = parentId
                leftToLeft = parentId
                rightToRight = parentId

                topMargin = 20.dp
                leftMargin = 24.dp
                rightMargin = 24.dp
            }
        }
    }

    private val divider by lazy {
        View(context).apply {
            id = R.id.viewDivider
            setBackgroundColor(Color.parseColor("#FFD9D9D9"))
            layoutParams = constraintLayoutParams(matchConstraint, 1) {
                topToBottom = R.id.tvTitle
                leftToLeft = parentId
                rightToRight = parentId

                topMargin = 16.dp
            }
        }
    }

    val content by lazy {
        MaterialTextView(context).apply {
            id = R.id.tvContent
            textSize = 14F
            setTextColor(context.colorInt(R.color.base_black_content))

            layoutParams = constraintLayoutParams(matchConstraint, wrapContent) {
                topToBottom = R.id.viewDivider
                leftToLeft = parentId
                rightToRight = parentId

                topMargin = 24.dp
                leftMargin = 24.dp
                rightMargin = 24.dp
            }
        }
    }

    val positiveButton by lazy {
        MaterialButton(context).apply {
            id = R.id.mbPositive
            textSize = 14F
            cornerRadius = 4.dp
            setTextColor(context.colorFromAttr(R.attr.colorOnPrimary))

            layoutParams = constraintLayoutParams(matchConstraint, wrapContent) {
                leftToLeft = parentId
                rightToLeft = R.id.mbNegative
                bottomToBottom = parentId
                topToBottom = R.id.tvContent

                topMargin = 24.dp
                bottomMargin = 16.dp
                leftMargin = 24.dp
                rightMargin = 24.dp

                verticalBias = 1F
                horizontalChainStyle = LayoutParams.CHAIN_PACKED
            }
        }
    }

    val negativeButton by lazy {
        MaterialButton(context).apply {
            id = R.id.mbNegative
            textSize = 14F
            cornerRadius = 4.dp
            setTextColor(context.colorFromAttr(R.attr.colorOnPrimary))
            setBackgroundColor(context.colorInt(R.color.base_dark_blue))

            layoutParams = constraintLayoutParams(matchConstraint, wrapContent) {
                leftToRight = R.id.mbPositive
                topToTop = R.id.mbPositive
                bottomToBottom = R.id.mbPositive
                rightToRight = parentId

                rightMargin = 24.dp
            }
        }
    }


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        clipToOutline = true
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                view?.let {
                    outline?.setRoundRect(0, 0, it.width, it.height, 4.dpFloat)
                }
            }
        }

        setBackgroundColor(context.colorInt(R.color.base_white))

        addView(title)
        addView(divider)
        addView(content)
        addView(positiveButton)
        addView(negativeButton)
    }

}
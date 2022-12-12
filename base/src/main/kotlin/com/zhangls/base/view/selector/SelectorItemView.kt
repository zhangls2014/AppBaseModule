package com.zhangls.base.view.selector

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.zhangls.base.R
import com.zhangls.base.extension.*

/**
 * 网页
 *
 * @author zhangls
 */
class SelectorItemView : ConstraintLayout {

    val text = MaterialTextView(context).apply {
        id = R.id.tvText
        maxLines = 1
        ellipsize = TextUtils.TruncateAt.END
        textSize = 16F
        setTextColor(context.colorInt(R.color.base_black_content))

        layoutParams = constraintLayoutParams(matchConstraint, wrapContent) {
            leftToLeft = parentId
            rightToRight = parentId
            topToTop = parentId
            bottomToBottom = parentId
        }
    }

    val check = ShapeableImageView(context).apply {
        id = R.id.ivCheck
        imageTintList = context.colorStateListFromAttr(R.attr.colorPrimary)
        setImageResource(R.drawable.base_ic_recycler_item_select_check)

        layoutParams = constraintLayoutParams(wrapContent, wrapContent) {
            rightToRight = parentId
            topToTop = parentId
            bottomToBottom = parentId
        }
    }


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        addView(text)
        addView(check)

        setPadding(16.dp)
        setBackgroundResource(context.resourceIdFromAttr(android.R.attr.selectableItemBackground))

        layoutParams = recyclerViewParams(matchParent, wrapContent)
    }

}
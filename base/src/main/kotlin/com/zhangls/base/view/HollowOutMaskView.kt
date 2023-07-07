package com.zhangls.base.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.zhangls.base.extension.colorFromAttr
import com.zhangls.base.extension.dpFloat


/**
 * 镂空遮罩 View
 *
 * 目前支持三种镂空方式：
 * 1. 圆形镂空
 * 2. 圆角矩形镂空
 * 3. 四个直角镂空
 *
 * 同时也支持自定义镂空区域，通过 [setHollowOutPath] 方法设置镂空区域。
 *
 * @author zhangls
 */
class HollowOutMaskView : View {
    // 遮罩
    private val boundsPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    }
    // 边框
    private val borderPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 2.dpFloat
        color = context.colorFromAttr(androidx.appcompat.R.attr.colorPrimary)
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
    }
    private val hollowPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 1F
        color = Color.TRANSPARENT
    }
    private val borderPath: Path = Path()
    private val hollowPath: Path = Path()

    @ColorInt
    private var maskColor = Color.parseColor("#7E000000")




    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)


    /**
     * 绘制圆
     *
     * @param x 中心点 x 坐标
     * @param y 中心点 y 坐标
     * @param radius 半径
     */
    fun setCircleHollowOut(x: Float, y: Float, radius: Float) {
        resetPath()
        hollowPath.addCircle(x, y, radius, Path.Direction.CW)
        borderPath.addCircle(x, y, radius, Path.Direction.CW)
    }

    /**
     * 绘制圆角矩形
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param roundCorner 圆角大小
     */
    fun setRoundRectHollowOut(
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
        roundCorner: Float,
    ) {
        resetPath()
        hollowPath.addRoundRect(left, top, right, bottom, roundCorner, roundCorner, Path.Direction.CW)
        borderPath.addRoundRect(left, top, right, bottom, roundCorner, roundCorner, Path.Direction.CW)
    }

    /**
     * 绘制四个直角
     *
     * @param horizontalLength 水平边长度
     * @param verticalLength 垂直边长度
     */
    fun setRightAngleHollowOut(
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
        horizontalLength: Float,
        verticalLength: Float
    ) {
        resetPath()

        hollowPath.addRect(left, top, right, bottom, Path.Direction.CW)

        with(borderPath) {
            reset()
            // 左上角
            moveTo(left, top + verticalLength)
            lineTo(left, top)
            lineTo(left + horizontalLength, top)

            // 右上角
            moveTo(right - horizontalLength, top)
            lineTo(right, top)
            lineTo(right, top + verticalLength)

            // 右下角
            moveTo(right, bottom - verticalLength)
            lineTo(right, bottom)
            lineTo(right - horizontalLength, bottom)

            // 左下角
            moveTo(left + horizontalLength, bottom)
            lineTo(left, bottom)
            lineTo(left, bottom - verticalLength)
        }
    }

    /**
     * 自定义镂空形状和边框
     */
    fun setHollowOutPath(hollowPath: Path, borderPath: Path) {
        resetPath()

        this.hollowPath.set(hollowPath)
        this.borderPath.set(borderPath)
    }

    override fun onDraw(canvas: Canvas) {
        // 离屏缓存，否则透明区域会显示默认的黑色
        val saved = canvas.saveLayer(0F, 0F, width.toFloat(), height.toFloat(), null)
        canvas.drawColor(maskColor)

        if (hollowPath.isEmpty.not()) {
            canvas.drawPath(hollowPath, boundsPaint)
            canvas.drawPath(hollowPath, hollowPaint)
        }
        if (borderPath.isEmpty.not()) {
            canvas.drawPath(borderPath, borderPaint)
        }

        canvas.restoreToCount(saved)
    }

    private fun resetPath() {
        borderPath.reset()
        hollowPath.reset()
    }

}
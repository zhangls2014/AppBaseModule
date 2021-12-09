package com.zhangls.base.view

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 重写 LinearLayoutManager，捕获异常，防止异常退出
 *
 * @author zhangls
 */
class PagingLinearLayoutManager(context: Context) : LinearLayoutManager(context) {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            // 为了实现 paging 的空列表显示占位图的需求，采用自定义 LayoutManager 的方式捕获异常
            // 在列表为空时，adapter 会自动添加一个占位图 item，导致数组越界
        }
    }

}
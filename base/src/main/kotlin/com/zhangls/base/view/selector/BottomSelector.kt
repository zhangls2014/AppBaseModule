package com.zhangls.base.view.selector

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.zhangls.base.R
import com.zhangls.base.extension.dp
import com.zhangls.base.extension.launchWhenResumed
import com.zhangls.base.extension.launchWhenStarted


/**
 * 选择 BottomSheet
 *
 * @author zhangls
 */
class BottomSelector private constructor() : BottomSheetDialogFragment() {
    private var recyclerView: RecyclerView? = null
    private lateinit var selectItems: List<SelectorItemModel>
    private val diffCallBack = object : DiffUtil.ItemCallback<SelectorItemModel>() {
        override fun areItemsTheSame(oldItem: SelectorItemModel, newItem: SelectorItemModel): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(oldItem: SelectorItemModel, newItem: SelectorItemModel): Boolean {
            return oldItem == newItem
        }
    }
    private val listener: OnCheckChangeListener by lazy {
        OnCheckChangeListener { voiceItemModel ->
            selectItems = selectItems.map { model ->
                if (voiceItemModel.itemId == model.itemId) {
                    val checkedItem = model.copy(isChecked = true)
                    checkChangeListener?.onChanged(checkedItem)
                    checkedItem
                } else {
                    model.copy(isChecked = false)
                }
            }
            selectorAdapter.submitList(selectItems)
        }
    }
    private val selectorAdapter: SelectorAdapter by lazy {
        SelectorAdapter(listener, diffCallBack)
    }
    var checkChangeListener: OnCheckChangeListener? = null


    companion object {
        fun newInstance(): BottomSelector = BottomSelector()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return RecyclerView(requireContext()).also { recyclerView = it }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView?.let {
            it.layoutManager = LinearLayoutManager(context)
            val divider = MaterialDividerItemDecoration(it.context, MaterialDividerItemDecoration.VERTICAL).apply {
                setDividerColorResource(it.context, R.color.base_grey_divider)
                dividerThickness = 1.dp
            }
            it.addItemDecoration(divider)
            it.adapter = selectorAdapter

            if (this::selectItems.isInitialized) {
                selectorAdapter.submitList(selectItems)
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        super.dismiss()
    }

    fun show(fragment: Fragment, tag: String? = null) {
        fragment.launchWhenStarted {
            super.show(fragment.childFragmentManager, tag)
        }
    }

    fun dismiss(fragment: Fragment) {
        fragment.launchWhenResumed {
            super.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerView?.adapter = null
        checkChangeListener = null
    }

    fun initItems(items: List<SelectorItemModel>): BottomSelector {
        if (this::selectItems.isInitialized.not()) {
            selectItems = items
        }
        return this
    }
}
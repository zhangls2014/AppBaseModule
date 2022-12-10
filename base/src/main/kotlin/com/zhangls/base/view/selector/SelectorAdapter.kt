package com.zhangls.base.view.selector

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.zhangls.base.R
import com.zhangls.base.extension.onClick


/**
 * @author zhangls
 */
internal class SelectorAdapter(
    private val listener: OnCheckChangeListener,
    diffCallback: DiffUtil.ItemCallback<SelectorItemModel>
) : ListAdapter<SelectorItemModel, SelectorAdapter.SelectViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectViewHolder {
        return SelectViewHolder(SelectorItemView(parent.context))
    }

    override fun onBindViewHolder(holder: SelectViewHolder, position: Int) {
        val model = getItem(position)
        holder.text.text = model.itemText
        holder.text.gravity = model.textGravity
        holder.check.isInvisible = model.isChecked.not()

        holder.item.onClick { listener.onChanged(model) }
    }

    override fun onBindViewHolder(holder: SelectViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty() && payloads[0] is Bundle) {
            val bundle = payloads[0] as Bundle
            if (bundle.containsKey("isChecked")) {
                val isChecked = bundle.getBoolean("isChecked")
                holder.check.isInvisible = isChecked.not()
            }
        } else {
            onBindViewHolder(holder, position)
        }
    }

    class SelectViewHolder(val item: View) : RecyclerView.ViewHolder(item) {
        val text: MaterialTextView = itemView.findViewById(R.id.tvText)
        val check: AppCompatImageView = itemView.findViewById(R.id.ivCheck)
    }

}
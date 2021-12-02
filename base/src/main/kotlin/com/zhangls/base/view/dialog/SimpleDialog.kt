package com.zhangls.base.view.dialog

import android.app.Dialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import com.zhangls.base.R
import com.zhangls.base.extension.onClick

/**
 * 简单信息对话框
 *
 * 支持通过 layoutId 自行扩展布局，但是必须包含如下 ID：
 * tvTitle
 * tvContent
 * mbPositive
 * mbNegative
 *
 * @author zhangls
 */
class SimpleDialog : AppCompatDialogFragment() {
    private lateinit var simpleView: View

    // 按钮点击事件回调
    var positiveCallback: (() -> Unit)? = null
    var negativeCallback: (() -> Unit)? = null


    companion object {
        private const val KEY_TITLE = "key_title"
        private const val KEY_CONTENT = "key_content"
        private const val KEY_POSITIVE_TEXT = "key_positive_text"
        private const val KEY_NEGATIVE_TEXT = "key_negative_text"
        private const val KEY_LAYOUT_ID = "key_layout_id"


        fun newInstance(
            title: String,
            content: String,
            positiveText: String,
            negativeText: String?,
            layoutId: Int? = null
        ): SimpleDialog {
            return SimpleDialog().apply {
                arguments = bundleOf(
                    KEY_TITLE to title,
                    KEY_CONTENT to content,
                    KEY_POSITIVE_TEXT to positiveText,
                    KEY_NEGATIVE_TEXT to negativeText,
                    KEY_LAYOUT_ID to layoutId
                )
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val layoutId = requireArguments().getInt(KEY_LAYOUT_ID)

        simpleView = if (layoutId > 0) {
            LayoutInflater.from(requireContext()).inflate(layoutId, container, true)
        } else {
            SimpleView(requireContext())
        }

        simpleView.also {
            val positiveButton = it.findViewById<TextView>(R.id.mbPositive)
            val negativeButton = it.findViewById<TextView>(R.id.mbNegative)

            with(requireArguments()) {
                it.findViewById<TextView>(R.id.tvTitle).text = getString(KEY_TITLE)
                it.findViewById<TextView>(R.id.tvContent).text = getString(KEY_CONTENT)
                positiveButton.text = getString(KEY_POSITIVE_TEXT)
                val negativeText = getString(KEY_NEGATIVE_TEXT)
                if (negativeText.isNullOrEmpty()) {
                    negativeButton.isVisible = false
                } else {
                    negativeButton.isVisible = true
                    negativeButton.text = getString(KEY_NEGATIVE_TEXT)
                }
            }

            negativeButton.onClick {
                negativeCallback?.invoke()
                dismiss()
            }
            positiveButton.onClick {
                positiveCallback?.invoke()
                dismiss()
            }
        }
        return simpleView
    }

    @Suppress("DEPRECATION")
    override fun onResume() {
        super.onResume()
        isCancelable = false

        setLayout(resources.configuration.orientation)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        dialog?.window?.let {
            setLayout(newConfig.orientation)
        }
    }

    private fun setLayout(orientation: Int) {
        dialog?.window?.let {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                // 竖屏
                it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            } else {
                // 横屏
                it.setLayout(resources.displayMetrics.widthPixels / 2, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }
}
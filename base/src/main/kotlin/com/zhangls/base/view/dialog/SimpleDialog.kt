package com.zhangls.base.view.dialog

import android.app.Dialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import com.zhangls.base.R
import com.zhangls.base.extension.hideSystemBars
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
        private const val KEY_HIDE_SYSTEM_BAR = "key_hide_system_bar"


        fun newInstance(
            title: CharSequence,
            content: CharSequence,
            positiveText: CharSequence,
            negativeText: CharSequence?,
            layoutId: Int? = null,
            hideSystemBar: Boolean = false,
        ): SimpleDialog {
            return SimpleDialog().apply {
                arguments = bundleOf(
                    KEY_TITLE to title,
                    KEY_CONTENT to content,
                    KEY_POSITIVE_TEXT to positiveText,
                    KEY_NEGATIVE_TEXT to negativeText,
                    KEY_LAYOUT_ID to layoutId,
                    KEY_HIDE_SYSTEM_BAR to hideSystemBar
                )
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).also {
            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.window?.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        }
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
                    negativeButton.text = negativeText
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.getBoolean(KEY_HIDE_SYSTEM_BAR) == true) {
            dialog?.window?.hideSystemBars()
        }
    }

    override fun onResume() {
        super.onResume()
        isCancelable = false

        setLayout(resources.configuration.orientation)
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
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

    fun show(manager: FragmentManager) {
        super.show(manager, null)
    }
}
package com.c7z.mappilogue_aos.presentation.ui.component.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ComponentDialog2ButtonEditTextBinding
import java.util.regex.Pattern

class ComponentDialogEditText(
    private val onSecondBtnClicked: (String) -> Unit,
    private val defaultNickName: String,
) : DialogFragment() {
    private lateinit var binding: ComponentDialog2ButtonEditTextBinding

    private val nickNameRegex = Regex("^[가-힣a-zA-Z._-]{1,8}\$")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.component_dialog_2_button_edit_text,
            container,
            false
        )
        initBinding()
        initUi()
        return binding.root
    }

    private fun initBinding() {
        binding.dialog = this
    }

    private fun initUi() {
        initText()
        initSecondClick()
        initCheckRegex()
    }

    private fun initText() {
        when (tag) {
            "EDIT_NICKNAME" -> case_EDIT_NICKNAME()
        }
    }

    private fun initSecondClick() {
        binding.componentDialogEditTextBtnSecondBtn.setOnClickListener {
            if (binding.componentDialogEditTextEdtContent.text.toString().checkRegex()) {
                onSecondBtnClicked.invoke(binding.componentDialogEditTextEdtContent.text.toString())
                onDismiss()
            }
        }
    }

    private fun initCheckRegex() {
        binding.componentDialogEditTextEdtContent.addTextChangedListener {
            showError(!it.toString().checkRegex())
        }
    }

    private fun String.checkRegex(): Boolean {
        return nickNameRegex.matches(this)
    }

    private fun showError(regexResult: Boolean) {
        binding.componentDialogEditTextLayoutError.visibility =
            if (regexResult) View.VISIBLE else View.GONE
    }

    private fun case_EDIT_NICKNAME() {
        binding.componentDialogEditTextEdtContent.apply {
            setText(defaultNickName)
            hint = "1~8자의 한글 또는 영문"
            filters = arrayOf(InputFilter.LengthFilter(8))
        }
        binding.componentDialogEditTextTvFirstBtn.text = "닫기"
        binding.componentDialogEditTextTvSecondBtn.text = "바꾸기"
        binding.componentDialogEditTextTvError.text = "초성이나 특수문자, 숫자는 넣을 수 없어요"
    }

    fun onDismiss() {
        dismiss()
    }

    /** Set Dialog Size **/
    override fun onResume() {
        super.onResume()
        val windowManager =
            requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x;
        params?.width = (deviceWidth * 0.7).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
package com.c7z.mappilogue_aos.presentation.ui.component.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ComponentDialog1ButtonBinding

class ComponentDialogOneButton (
    /** 두 번째 버튼 클릭시 **/
    private val onSecondBtnClicked : () -> Unit,
        ): DialogFragment() {
    private lateinit var binding : ComponentDialog1ButtonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.component_dialog_1_button, container, false)
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
    }

    private fun initText() {
        when(tag) {
            "SUCCESS_SIGN_OUT" -> case_SUCCESS_SIGN_OUT()
        }
    }

    private fun initSecondClick() {
        binding.componentDialog1ButtonTvSecondBtn.setOnClickListener {
            onSecondBtnClicked.invoke()
            onDismiss()
        }
    }

    private fun case_SUCCESS_SIGN_OUT() {
        binding.componentDialog1ButtonTvTitle.text = "탈퇴가 완료되었어요"
        binding.componentDialog1ButtonTvSecondBtn.text = "로그인 페이지로 돌아가기"
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
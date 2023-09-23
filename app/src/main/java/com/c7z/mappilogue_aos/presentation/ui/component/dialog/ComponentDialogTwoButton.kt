package com.c7z.mappilogue_aos.presentation.ui.component.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ComponentDialog2ButtonBinding

class ComponentDialogTwoButton (
    /** 두 번째 버튼 클릭시 **/
    private val onSecondBtnClicked : () -> Unit,
    /** 두 번째 버튼 색상 지정 **/
    private val setButtonColor : String,
        ): DialogFragment() {
    private lateinit var binding : ComponentDialog2ButtonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.component_dialog_2_button, container, false)
        initBinding()
        initUi()
        return binding.root
    }

    private fun initBinding() {
        binding.dialog = this
    }

    private fun initUi() {
        initText()
        initSecondButtonColor()
        initSecondClick()
    }

    private fun initText() {
        when(tag) {
            "STOP_WRITING_TODO" -> case_STOP_WRITING_TODO()
            "LOG_OUT" -> case_LOG_OUT()
        }
    }

    private fun initSecondButtonColor() {
        binding.componentDialog2ButtonBtnSecondBtn.setBackgroundResource(
            when(setButtonColor) {
            "RED" -> R.drawable.bg_red1_12
            else -> R.drawable.bg_green1_12
        })
    }

    private fun initSecondClick() {
        binding.componentDialog2ButtonTvSecondBtn.setOnClickListener {
            onSecondBtnClicked.invoke()
            onDismiss()
        }
    }

    private fun case_STOP_WRITING_TODO() {
        binding.componentDialog2ButtonTvTitle.text = "일정 작성을 중단할까요?"
        binding.componentDialog2ButtonTvContent.text = "저장하지 않은 일정은 사라져요"
        binding.componentDialog2ButtonTvFirstBtn.text = "취소"
        binding.componentDialog2ButtonTvSecondBtn.text = "나가기"
    }

    private fun case_LOG_OUT() {
        binding.componentDialog2ButtonTvTitle.text = "로그아웃 할까요?"
        binding.componentDialog2ButtonTvFirstBtn.text = "취소"
        binding.componentDialog2ButtonTvSecondBtn.text = "확인"
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
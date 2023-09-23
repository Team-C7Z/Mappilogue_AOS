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
import com.c7z.mappilogue_aos.databinding.ComponentDialog2ButtonCheckBoxBinding

class ComponentDialogCheckBox (
    /** 두 번째 버튼 클릭시 **/
    private val onSecondBtnClicked : () -> Unit,
    /** 두 번째 버튼 색상 지정 **/
    private val setButtonColor : String,
        ): DialogFragment() {
    private lateinit var binding : ComponentDialog2ButtonCheckBoxBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.component_dialog_2_button_check_box, container, false)
        initBinding()
        initUi()
        return binding.root
    }

    private fun initBinding() {
        binding.dialog = this
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initUi() {
        initText()
        initSecondButtonEnabled()
        initSecondButtonColor()
        initSecondClick()
    }

    private fun initText() {
        when(tag) {
            "SIGN_OUT" -> case_SIGN_OUT()
        }
    }

    private fun initSecondButtonEnabled() {
        binding.componentDialog2ButtonBtnSecondBtn.isEnabled = false //초기설정이 enable로 나와서 강제로 false로 설정
        binding.componentDialog2ButtonCheckboxCheck.setOnCheckedChangeListener { _, b ->
            binding.componentDialog2ButtonBtnSecondBtn.isEnabled = b
        }
    }

    private fun initSecondButtonColor() {
        binding.componentDialog2ButtonBtnSecondBtn.setBackgroundResource(
            when(setButtonColor) {
            "RED" -> R.drawable.selector_red1_12
            else -> R.drawable.bg_green1_12
        })
    }

    private fun initSecondClick() {
        binding.componentDialog2ButtonBtnSecondBtn.setOnClickListener {
            onSecondBtnClicked.invoke()
            onDismiss()
        }
    }

    private fun case_SIGN_OUT() {
        binding.componentDialog2ButtonTvTitle.text = "정말 탈퇴하시겠어요?"
        binding.componentDialog2ButtonTvContent.text = "이때까지 저장된 기록과 정보가 모두 지워지고\n이는 복구할 수 없어요"
        binding.componentDialog2ButtonCheckboxTvContent.text = "주의사항을 확인했어요"
        binding.componentDialog2ButtonTvFirstBtn.text = "취소"
        binding.componentDialog2ButtonTvSecondBtn.text = "탈퇴하기"
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
        dialog?.setCancelable(false)
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun checkBox() = binding.componentDialog2ButtonCheckboxCheck
}
package com.c7z.mappilogue_aos.presentation.ui.component.toast

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ComponentToastBinding

class ComponentAlertToast : DialogFragment() {
    private lateinit var binding: ComponentToastBinding
    private var handler : Handler? = Handler()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.component_toast, container, false)

        initBinding()
        initUi()

        return binding.root
    }

    private fun initBinding() {
    }

    override fun onResume() {
        super.onResume()
        val windowManager =
            requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.95).toInt()
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window?.setDimAmount(0F)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }

    private fun initUi() {
        binding.componentToastText.text = when (tag) {
            "COPY_EMAIL" -> case_COPY_EMAIL()
            else -> tag
        }
            handler?.postDelayed(Runnable {
            dismiss()
        }, 2000)
    }

    private fun case_COPY_EMAIL() = "이메일이 복사되었어요"

    override fun onDestroy() {
        super.onDestroy()
        handler = null
    }
}
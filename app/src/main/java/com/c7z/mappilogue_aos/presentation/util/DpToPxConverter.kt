package com.c7z.mappilogue_aos.presentation.util

import android.content.Context
import android.util.TypedValue

object DpToPxConverter {
    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }
}
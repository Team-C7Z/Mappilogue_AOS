package com.c7z.mappilogue_aos.presentation.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.c7z.mappilogue_aos.R

object ItemDecorator {

    class VerticalItemDecorator(private var spacing: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            if (parent.getChildAdapterPosition(view) != 0) outRect.top = spacing
        }
    }

    class HorizontalItemDecorator(private var spacing: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            if (parent.getChildAdapterPosition(view) != 0) outRect.left = spacing
        }
    }

    class MyPageItemDecorator(private val context : Context) : RecyclerView.ItemDecoration() {
        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDrawOver(c, parent, state)
            val height = DpToPxConverter.dpToPx(1f, context)

            val left = parent.paddingLeft.toFloat()
            val right = parent.width - parent.paddingRight.toFloat()
            val paint = Paint().apply { color = context.resources.getColor(R.color.gray_2, null) }
            for(i in 0 until parent.childCount) {
                val view = parent.getChildAt(i)
                val top = view.bottom.toFloat() + (view.layoutParams as RecyclerView.LayoutParams).bottomMargin
                val bottom = top + height

                c.drawRect(left, top, right, bottom, paint)
            }
        }
    }
}
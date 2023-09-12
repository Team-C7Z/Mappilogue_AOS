package com.c7z.mappilogue_aos.presentation.util

import android.view.MotionEvent
import android.widget.ImageView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.c7z.mappilogue_aos.R

class ItemTouchCallback(private val listener: ItemTouchHelperListener): ItemTouchHelper.Callback() {
    var dragFlags = 0
        /** 드래그 방향과 드래그 이동을 정의하는 함수 */
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        // 드래그 방향
        viewHolder.itemView.findViewById<ImageView>(R.id.item_add_todo_locations_iv_handle).setOnTouchListener { view, motionEvent ->
            dragFlags = if(motionEvent.action == MotionEvent.ACTION_DOWN || motionEvent.action == MotionEvent.ACTION_MOVE) {
                ItemTouchHelper.UP or ItemTouchHelper.DOWN
            } else 0
            return@setOnTouchListener true
        }
        // 드래그 이동을 만드는 함수
        return makeMovementFlags(dragFlags, 0)
    }

    /** 아이템이 움직일떼 호출되는 함수 */
    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        listener.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return false
    }

    /** 아이템이 스와이프 될때 호출되는 함수 */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
}
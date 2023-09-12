package com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.c7z.mappilogue_aos.data.remote.response.ResponseTodoColor
import com.c7z.mappilogue_aos.databinding.ItemAddTodoRvSetColorBinding

class AddTodoColorAdapter(
    private val onColorClicked : (ResponseTodoColor.ResultTodoColor) -> Unit
) : RecyclerView.Adapter<AddTodoColorAdapter.AddTodoColorViewHolder>() {
    var colors = mutableListOf<ResponseTodoColor.ResultTodoColor>()

    inner class AddTodoColorViewHolder(private val binding : ItemAddTodoRvSetColorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : ResponseTodoColor.ResultTodoColor) {
            binding.hexCode = item.code.toColorInt()
            binding.root.setOnClickListener { onColorClicked.invoke(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTodoColorViewHolder {
        return AddTodoColorViewHolder(ItemAddTodoRvSetColorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = colors.size

    override fun onBindViewHolder(holder: AddTodoColorViewHolder, position: Int) {
        holder.bind(colors[position])
    }
}
package com.c7z.mappilogue_aos.presentation.ui.main.mypage.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c7z.mappilogue_aos.data.data.MyPageMenuItem
import com.c7z.mappilogue_aos.databinding.ItemRvMyPageMenuBinding

class MyPageMenuAdapter(
    private val menuItems: List<MyPageMenuItem>,
    private val onItemClicked: (Int) -> Unit
) : RecyclerView.Adapter<MyPageMenuAdapter.MyPageMenuViewHolder>() {
    inner class MyPageMenuViewHolder(private val binding: ItemRvMyPageMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyPageMenuItem) {
            binding.data = item
            binding.root.setOnClickListener { onItemClicked.invoke(absoluteAdapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageMenuViewHolder {
        return MyPageMenuViewHolder(
            ItemRvMyPageMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = menuItems.size

    override fun onBindViewHolder(holder: MyPageMenuViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }
}
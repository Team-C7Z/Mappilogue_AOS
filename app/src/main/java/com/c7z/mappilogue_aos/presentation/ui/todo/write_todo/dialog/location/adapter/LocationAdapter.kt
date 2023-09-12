package com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.dialog.location.adapter

import android.provider.DocumentsContract.Document
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.c7z.mappilogue_aos.data.remote.response.ResponseKakaoLocation
import com.c7z.mappilogue_aos.databinding.ItemAddTodoSearchLocationBinding

class LocationAdapter(
    private val itemClick : (ResponseKakaoLocation.Document) -> Unit
) : PagingDataAdapter<ResponseKakaoLocation.Document, LocationAdapter.LocationViewHolder>(
    diffCallback) {
    inner class LocationViewHolder(private val binding : ItemAddTodoSearchLocationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : ResponseKakaoLocation.Document) {
            binding.item = item
            binding.root.setOnClickListener { itemClick.invoke(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(ItemAddTodoSearchLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ResponseKakaoLocation.Document>() {

            override fun areItemsTheSame(oldItem: ResponseKakaoLocation.Document, newItem: ResponseKakaoLocation.Document): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResponseKakaoLocation.Document, newItem: ResponseKakaoLocation.Document): Boolean {
                return oldItem == newItem
            }
        }
    }
}
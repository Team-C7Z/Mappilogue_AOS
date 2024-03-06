package com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.c7z.mappilogue_aos.data.remote.response.ResponseKakaoLocation
import com.c7z.mappilogue_aos.databinding.ItemAddTodoLocationsBinding
import com.c7z.mappilogue_aos.presentation.util.ItemTouchHelperListener

class AddTodoLocationAdapter(
    private val onChecked: (Int, Boolean) -> Unit,
    private val onTimeClicked : (Int) -> Unit
) : RecyclerView.Adapter<AddTodoLocationAdapter.AddTodoLocationViewHolder>(),
    ItemTouchHelperListener {
    var locationData = mutableListOf<ResponseKakaoLocation.Document>()
    var checkStatus = false

    inner class AddTodoLocationViewHolder(private val binding: ItemAddTodoLocationsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseKakaoLocation.Document) {
            binding.data = item.apply { if(item.time.isNullOrEmpty()) item.time = "설정 안 함" }
            binding.checkStatus = checkStatus

            binding.itemAddTodoLocationsCheckCheck.isChecked = item.isChecked
            binding.itemAddTodoLocationsCheckCheck.setOnClickListener { it ->
                item.isChecked = item.isChecked.not().also { _ ->
                    onChecked.invoke(absoluteAdapterPosition, (it as CheckBox).isChecked)
                }
            }

            binding.itemAddTodoLocationsTvTime.setOnClickListener { onTimeClicked.invoke(absoluteAdapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTodoLocationViewHolder {
        return AddTodoLocationViewHolder(
            ItemAddTodoLocationsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int = locationData.size

    override fun onBindViewHolder(holder: AddTodoLocationViewHolder, position: Int) {
        holder.bind(locationData[position])
    }

    fun initSelectClicked() {
        for (i in 0 until locationData.size) {
            locationData[i].isChecked = false
        }
        notifyDataSetChanged()
    }

    override fun onItemMove(from: Int, to: Int) {
        val item = locationData[from]
        locationData.removeAt(from)
        locationData.add(to, item)
        notifyItemMoved(from, to)
    }
}
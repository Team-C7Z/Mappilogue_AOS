package com.c7z.mappilogue_aos.presentation.ui.sign_out_reason.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c7z.mappilogue_aos.data.data.SignOutReasonItem
import com.c7z.mappilogue_aos.databinding.ItemRvSignOutReasonChecklistBinding

class SignOutReasonAdapter(
    private val reasonItems: List<SignOutReasonItem>,
    private val onItemClicked: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<SignOutReasonAdapter.SignOutReasonViewHolder>() {

    inner class SignOutReasonViewHolder(private val binding: ItemRvSignOutReasonChecklistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SignOutReasonItem) {
            binding.item = item
            binding.itemRvSignOutReasonChecklistCheckbox.setOnClickListener { onItemClicked.invoke(absoluteAdapterPosition, binding.itemRvSignOutReasonChecklistCheckbox.isChecked) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignOutReasonViewHolder {
        return SignOutReasonViewHolder(
            ItemRvSignOutReasonChecklistBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int = reasonItems.size

    override fun onBindViewHolder(holder: SignOutReasonViewHolder, position: Int) {
        holder.bind(reasonItems[position])
    }
}
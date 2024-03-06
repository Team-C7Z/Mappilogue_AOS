package com.c7z.mappilogue_aos.presentation.ui.gallery.single_select.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c7z.mappilogue_aos.data.data.GalleryData
import com.c7z.mappilogue_aos.databinding.ItemCustomGallerySingleImageFormBinding

class GalleryAdapter(private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    var galleryData = mutableListOf<GalleryData>()
    var checkedPosition = null
    inner class GalleryViewHolder(private val binding: ItemCustomGallerySingleImageFormBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GalleryData) {
            binding.galleryData = item
            binding.image.setImageURI(item.uri)
            binding.root.setOnClickListener {
                    onClick.invoke(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GalleryViewHolder {
        return GalleryViewHolder(
            ItemCustomGallerySingleImageFormBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int = galleryData.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(galleryData[position])
    }
}
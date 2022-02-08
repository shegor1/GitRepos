package com.shegor.gitrepos.ui.gitRepoDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shegor.gitrepos.databinding.TextItemViewBinding

class ShaListRvAdapter :
    ListAdapter<String, ShaListRvAdapter.ShaTextItemViewHolder>(ShaDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShaTextItemViewHolder {
        return ShaTextItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ShaTextItemViewHolder, position: Int) {
        val item = getItem(position)
        return holder.bind(item)
    }


    class ShaTextItemViewHolder private constructor(private val binding: TextItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.shaTextView.text = item
        }

        companion object {
            fun from(parent: ViewGroup): ShaTextItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TextItemViewBinding.inflate(layoutInflater, parent, false)
                return ShaTextItemViewHolder(binding)
            }
        }
    }

    class ShaDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
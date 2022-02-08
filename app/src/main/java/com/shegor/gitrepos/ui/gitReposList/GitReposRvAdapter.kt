package com.shegor.gitrepos.ui.gitReposList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shegor.gitrepos.databinding.GitRepoItemBinding
import com.shegor.gitrepos.domain.GitRepo

class GitReposRvAdapter(private val clickListener: GitRepoClickListener) :
    PagingDataAdapter<GitRepo, GitReposRvAdapter.GitRepoViewHolder>(GitReposDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoViewHolder {
        return GitRepoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GitRepoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }


    class GitRepoViewHolder private constructor(private val binding: GitRepoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GitRepo?, clickListener: GitRepoClickListener) {
            binding.gitRepo = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): GitRepoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GitRepoItemBinding.inflate(layoutInflater, parent, false)
                return GitRepoViewHolder(binding)
            }
        }
    }

    class GitReposDiffCallback : DiffUtil.ItemCallback<GitRepo>() {
        override fun areItemsTheSame(oldItem: GitRepo, newItem: GitRepo): Boolean {
            return oldItem.fullName == newItem.fullName
        }

        override fun areContentsTheSame(oldItem: GitRepo, newItem: GitRepo): Boolean {
            return oldItem == newItem
        }
    }
}

class GitRepoClickListener(
    val gitRepoClickListener: (gitRepo: GitRepo) -> Unit
) {
    fun onClick(gitRepo: GitRepo) = gitRepoClickListener(gitRepo)
}
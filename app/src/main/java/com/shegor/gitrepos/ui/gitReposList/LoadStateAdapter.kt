package com.shegor.gitrepos.ui.gitReposList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shegor.gitrepos.databinding.GitRepoLoaderBinding

class LoaderStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoaderStateAdapter.LoaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder.from(parent, retry)
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


    class LoaderViewHolder private constructor(
        private val binding: GitRepoLoaderBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup, retry: () -> Unit): LoaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = GitRepoLoaderBinding.inflate(inflater, parent, false)
                return LoaderViewHolder(binding, retry)
            }
        }

        init {
            binding.retryButton.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.tryAgainMessage.isVisible = loadState is LoadState.Error
        }
    }
}
package com.shegor.gitrepos.ui.gitRepoDetails

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.shegor.gitrepos.R
import com.shegor.gitrepos.base.baseUi.BaseFragment
import com.shegor.gitrepos.base.baseUi.BaseViewModelFactory
import com.shegor.gitrepos.databinding.GitReposDetailsFragmentBinding
import com.shegor.gitrepos.network.GitReposApi
import com.shegor.gitrepos.repository.GitReposRepository
import com.shegor.gitrepos.ui.gitReposList.GitReposListFragment.Companion.KEY_GIT_REPO_ARG

class GitRepoDetailsFragment :
    BaseFragment<GitRepoDetailsViewModel, GitReposDetailsFragmentBinding>() {

    companion object {
        fun newInstance() = GitRepoDetailsFragment()
        const val REPO_DETAILS_FRAGMENT_TAG = "GitRepoDetailsFragment"
    }

    private var _recyclerViewAdapter: ShaListRvAdapter? =
        null
    private val recyclerViewAdapter: ShaListRvAdapter
        get() = _recyclerViewAdapter!!

    override val layoutId = R.layout.git_repos_details_fragment

    override fun getViewModel() = GitRepoDetailsViewModel::class.java

    override fun getViewModelFactory() = BaseViewModelFactory(
        GitRepoDetailsViewModel::class.java
    ) {
        GitRepoDetailsViewModel(
            GitReposRepository(GitReposApi.gitReposRetrofitService),
            requireArguments().getParcelable(KEY_GIT_REPO_ARG)
        )
    }

    override fun connectDataBinding() {
        binding.includedGitRepo.gitRepo = viewModel.gitRepo
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLastCommit()
        setupRecyclerView()
        setObservers()
    }


    private fun getLastCommit() {
        viewModel.getLastCommit()
    }

    private fun setupRecyclerView() {
        binding.parentsShaRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        _recyclerViewAdapter = ShaListRvAdapter()
        binding.parentsShaRecyclerView.adapter = recyclerViewAdapter
    }

    private fun setObservers() {
        viewModel.commit.observe(viewLifecycleOwner) {
            binding.commit = it
            recyclerViewAdapter.submitList(it.parentsShaList)
        }

        viewModel.loadingStatus.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it == GitRepoDetailsViewModel.LoadingStatus.LOADING
            binding.connectionProblemsMessage.isVisible =
                it == GitRepoDetailsViewModel.LoadingStatus.ERROR
            binding.retryButton.isVisible = it == GitRepoDetailsViewModel.LoadingStatus.ERROR
            binding.allParentShaTitle.isVisible = it == GitRepoDetailsViewModel.LoadingStatus.DONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _recyclerViewAdapter = null
    }
}
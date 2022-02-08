package com.shegor.gitrepos.ui.gitReposList

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.shegor.gitrepos.R
import com.shegor.gitrepos.base.baseUi.BaseFragment
import com.shegor.gitrepos.base.baseUi.BaseViewModelFactory
import com.shegor.gitrepos.databinding.GitReposListFragmentBinding
import com.shegor.gitrepos.domain.GitRepo
import com.shegor.gitrepos.network.GitReposApi
import com.shegor.gitrepos.repository.GitReposRepository
import com.shegor.gitrepos.ui.gitRepoDetails.GitRepoDetailsFragment
import com.shegor.gitrepos.ui.gitRepoDetails.GitRepoDetailsFragment.Companion.REPO_DETAILS_FRAGMENT_TAG
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GitReposListFragment : BaseFragment<GitReposListViewModel, GitReposListFragmentBinding>() {

    companion object {
        fun newInstance() = GitReposListFragment()
        const val GIT_REPO_LIST_FRAGMENT_TAG = "GitRepoListFragment"
        const val KEY_GIT_REPO_ARG = "GitRepoDataBundleKey"
    }

    private var _recyclerViewAdapter: PagingDataAdapter<GitRepo, GitReposRvAdapter.GitRepoViewHolder>? =
        null
    private val recyclerViewAdapter: PagingDataAdapter<GitRepo, GitReposRvAdapter.GitRepoViewHolder>
        get() = _recyclerViewAdapter!!

    override val layoutId = R.layout.git_repos_list_fragment

    override fun getViewModel() = GitReposListViewModel::class.java

    override fun getViewModelFactory() = BaseViewModelFactory(GitReposListViewModel::class.java) {
        GitReposListViewModel(GitReposRepository(GitReposApi.gitReposRetrofitService))
    }

    override fun connectDataBinding() {
        super.connectDataBinding()
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        displayLoadingState()
        setListeners()
        setObservers()
    }

    private fun setupRecyclerView() {
        _recyclerViewAdapter = GitReposRvAdapter(GitRepoClickListener {
            viewModel.navigateToGitReposDetailsFragment(it)
        })
        val loaderStateAdapter = LoaderStateAdapter { recyclerViewAdapter.retry() }

        binding.gitReposRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerViewAdapter
                .withLoadStateFooter(LoaderStateAdapter { recyclerViewAdapter.retry() })
        }
    }

    private fun displayLoadingState() {
        lifecycleScope.launch {
            recyclerViewAdapter.loadStateFlow.collect { loadState ->
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
                binding.connectionProblemsTextView.isVisible =
                    loadState.source.refresh is LoadState.Error
            }
        }
    }

    private fun setListeners() {
        binding.retryButton.setOnClickListener {
            recyclerViewAdapter.retry()
        }
    }

    private fun setObservers() {

        viewModel.gitReposList.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                recyclerViewAdapter.submitData(it)
            }
        }

        viewModel.navigationToGitReposDetails.observe(viewLifecycleOwner) {
            it?.let {
                val bundle = Bundle()
                bundle.putParcelable(KEY_GIT_REPO_ARG, it)

                val fragment = GitRepoDetailsFragment.newInstance()
                fragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        fragment,
                        REPO_DETAILS_FRAGMENT_TAG
                    )
                    .addToBackStack(null)
                    .commit()

                viewModel.finishNavigationToGitReposDetailsFragment()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _recyclerViewAdapter = null
    }
}
package com.shegor.gitrepos.ui.gitReposList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shegor.gitrepos.domain.GitRepo
import com.shegor.gitrepos.repository.GitReposRepository

class GitReposListViewModel(private val repository: GitReposRepository) : ViewModel() {

    private val _navigationToGitReposDetails = MutableLiveData<GitRepo?>()
    val navigationToGitReposDetails: LiveData<GitRepo?>
        get() = _navigationToGitReposDetails

    var gitReposList: LiveData<PagingData<GitRepo>> = getGitRepos()

    private fun getGitRepos(): LiveData<PagingData<GitRepo>> {
        return repository.getGitReposLiveData().cachedIn(viewModelScope)
    }

    fun navigateToGitReposDetailsFragment(gitRepo: GitRepo) {
        _navigationToGitReposDetails.value = gitRepo
    }

    fun finishNavigationToGitReposDetailsFragment() {
        _navigationToGitReposDetails.value = null
    }
}
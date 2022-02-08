package com.shegor.gitrepos.ui.gitRepoDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shegor.gitrepos.domain.Commit
import com.shegor.gitrepos.domain.GitRepo
import com.shegor.gitrepos.repository.GitReposRepository
import kotlinx.coroutines.launch

class GitRepoDetailsViewModel(private val repository: GitReposRepository, val gitRepo: GitRepo?) :
    ViewModel() {

    enum class LoadingStatus { LOADING, DONE, ERROR }

    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    private val _commit = MutableLiveData<Commit>()
    val commit: LiveData<Commit>
        get() = _commit

    fun getLastCommit() {
        val url = gitRepo?.commitsUrl ?: return

        viewModelScope.launch {
            try {
                _loadingStatus.value = LoadingStatus.LOADING
                _commit.value = repository.getLastCommit(url)
                _loadingStatus.value = LoadingStatus.DONE
            } catch (e: Exception) {
                _loadingStatus.value = LoadingStatus.ERROR
                Log.e("Error", "The network request failed due to ${e.message}")
            }
        }
    }
}
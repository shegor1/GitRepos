package com.shegor.gitrepos.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.*
import com.shegor.gitrepos.base.BaseRepository
import com.shegor.gitrepos.domain.Commit
import com.shegor.gitrepos.domain.GitRepo
import com.shegor.gitrepos.network.IGitReposService

class GitReposRepository(private val gitReposService: IGitReposService) : BaseRepository() {

    companion object {
        const val INITIAL_URL =
            "https://api.github.com/repositories?accept=application/vnd.github.v3+json"
        const val DEFAULT_PAGE_SIZE = 20
        const val NETWORK_ERROR = "Network Error"
    }

    fun getGitReposLiveData(pagingConfig: PagingConfig = getPagingConfig()): LiveData<PagingData<GitRepo>> {
        val dtoPagingLiveData = Pager(
            pagingConfig,
            INITIAL_URL,
            { GitReposPagingSource(gitReposService) }).liveData
        return dtoPagingLiveData.map { pagingData ->
            pagingData.map {
                it.toDomain()
            }
        }
    }

    private fun getPagingConfig(): PagingConfig =
        PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            enablePlaceholders = true
        )

    suspend fun getLastCommit(url: String): Commit? {
        return apiCall({ gitReposService.getCommits(url) }, NETWORK_ERROR)?.first()?.toDomain()

    }
}
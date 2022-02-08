package com.shegor.gitrepos.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shegor.gitrepos.network.GitRepoDto
import com.shegor.gitrepos.network.IGitReposService
import com.shegor.gitrepos.repository.GitReposRepository.Companion.INITIAL_URL
import com.shegor.gitrepos.utils.UrlParseUtils
import retrofit2.HttpException
import java.io.IOException

class GitReposPagingSource(private val gitReposService: IGitReposService) :
    PagingSource<String, GitRepoDto>() {

    companion object {
        const val REL_NEXT = "next"
    }

    override fun getRefreshKey(state: PagingState<String, GitRepoDto>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, GitRepoDto> {

        val linkToPage = params.key ?: INITIAL_URL
        return try {
            val response = gitReposService.getGitRepos(linkToPage)

            val parsedLinkHeader = UrlParseUtils.parseLinkHeader(response.headers().get("link"))
            val repos = checkNotNull(response.body())

            LoadResult.Page(repos, null, parsedLinkHeader[REL_NEXT])

        } catch (e: IOException) {
            Log.e("Error", "The network request failed due to ${e.message}")
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.e("Error", "The network request failed due to ${e.message}")
            return LoadResult.Error(e)
        }
    }
}
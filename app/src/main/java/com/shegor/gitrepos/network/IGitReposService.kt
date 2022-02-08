package com.shegor.gitrepos.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

private const val BASE_URL = "https://api.github.com/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface IGitReposService {
    @GET
    suspend fun getGitRepos(
        @Url url: String,
        @Query("accept") accept: String = "application/vnd.github.v3+json"
    ): Response<List<GitRepoDto>>

    @GET
    suspend fun getCommits(
        @Url url: String
    ): Response<List<CommitContainerDto>>
}

object GitReposApi {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val gitReposRetrofitService: IGitReposService = retrofit.create(IGitReposService::class.java)
}

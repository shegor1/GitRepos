package com.shegor.gitrepos.network

import com.shegor.gitrepos.domain.Commit
import com.shegor.gitrepos.domain.GitRepo
import com.shegor.gitrepos.utils.UrlParseUtils
import com.squareup.moshi.Json

data class GitRepoDto(

    @Json(name = "full_name")
    val fullName: String,
    val owner: GitRepoOwnerDto,
    @Json(name = "commits_url")
    val commitsUrl: String
) {
    data class GitRepoOwnerDto(

        val login: String,
        @Json(name = "avatar_url")
        val avatarUrl: String
    )

    fun toDomain() = GitRepo(
        fullName = fullName,
        ownerLogin = owner.login,
        ownerAvatarUrl = owner.avatarUrl,
        commitsUrl = UrlParseUtils.cutUrl(commitsUrl)
    )
}

data class CommitContainerDto(
    val commit: CommitDto,
    val parents: List<CommitDto.ParentDto>
) {

    data class CommitDto(
        val message: String,
        val author: AuthorDto,
    ) {

        data class AuthorDto(
            val name: String,
            val date: String
        )

        data class ParentDto(
            val sha: String
        )
    }

    fun toDomain() = Commit(
        message = commit.message,
        authorName = commit.author.name,
        date = commit.author.date,
        parentsShaList = parents.map { it.sha }
    )
}

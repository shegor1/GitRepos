package com.shegor.gitrepos.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GitRepo(
    val fullName: String,
    val ownerLogin: String,
    val ownerAvatarUrl: String,
    val commitsUrl: String
) : Parcelable

data class Commit(
    val message: String,
    val authorName: String,
    val date: String,
    val parentsShaList: List<String>
)
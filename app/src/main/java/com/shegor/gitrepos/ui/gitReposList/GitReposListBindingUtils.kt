package com.shegor.gitrepos.ui.gitReposList

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.shegor.gitrepos.R

@BindingAdapter("repoFullName")
fun setGitRepoFullName(textView: TextView, fullName: String?) {
    fullName?.let {
        textView.text = textView.context.resources.getString(R.string.repo_full_name_template, it)
    }
}

@BindingAdapter("authorLogin")
fun setGitRepoAuthorLogin(textView: TextView, login: String?) {
    login?.let {
        textView.text = textView.context.resources.getString(R.string.author_login_template, it)
    }
}

@BindingAdapter("ownerAvatarUrl")
fun setNewsImage(imageView: ImageView, url: String?) {

    url?.let {

        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
            .error(R.drawable.image_placeholder)
            .placeholder(ColorDrawable(Color.GRAY))
            .into(imageView)
        return@setNewsImage
    }
    imageView.setImageResource(R.drawable.image_placeholder)

}
package com.shegor.gitrepos.ui.gitRepoDetails

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.shegor.gitrepos.R
import com.shegor.gitrepos.utils.DateUtils

@BindingAdapter("commitAuthor")
fun setCommitAuthor(textView: TextView, author: String?) {
    author?.let {
        textView.text = textView.context.resources.getString(R.string.commit_author_template, it)
    }
}

@BindingAdapter("commitDate")
fun setCommitDate(textView: TextView, date: String?) {
    date?.let {
        textView.text = textView.context.resources.getString(
            R.string.commit_date_template,
            DateUtils.formatJsonDate(it)
        )
    }
}
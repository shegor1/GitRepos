package com.shegor.gitrepos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shegor.gitrepos.ui.gitReposList.GitReposListFragment
import com.shegor.gitrepos.ui.gitReposList.GitReposListFragment.Companion.GIT_REPO_LIST_FRAGMENT_TAG

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, GitReposListFragment.newInstance(), GIT_REPO_LIST_FRAGMENT_TAG)
            .commitNow()
    }
}

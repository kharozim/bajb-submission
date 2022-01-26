package com.bajp.submissionthree.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bajp.submissionthree.ui.home.content.HomeFragment

class HomeViewPager(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val isMovie = position == 0
        return HomeFragment.onSaveInstance(isMovie)
    }
}
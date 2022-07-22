package com.repo01.repoapp.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.repo01.repoapp.ui.main.tab.issue.IssueFragment
import com.repo01.repoapp.ui.main.tab.notifications.NotificationsFragment

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IssueFragment()
            else -> NotificationsFragment()
        }
    }

}
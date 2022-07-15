package com.repo01.repoapp.ui.main.tab.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.repo01.repoapp.R
import com.repo01.repoapp.databinding.FragmentNotificationsBinding
import com.repo01.repoapp.ui.main.tab.notifications.adapter.NotificationsItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private val notificationsViewModel: NotificationsViewModel by viewModels()
    private val notificationsAdpater by lazy { NotificationsItemAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeData()
    }

    private fun initView() {
        setRecyclerView()
        getNotificationsData()
    }

    private fun setRecyclerView() {
        binding.rvNotificationsList.apply {
            adapter = notificationsAdpater
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getNotificationsData() {
        notificationsViewModel.getNotifications()
    }

    private fun observeData() {
        observeNotificationsData()
        observeProgressBarVisible()
    }

    private fun observeNotificationsData() {
        notificationsViewModel.notificationList.observe(viewLifecycleOwner) {
            notificationsAdpater.submitList(it.toList())
        }
    }

    private fun observeProgressBarVisible(){
        notificationsViewModel.progressBarVisible.observe(viewLifecycleOwner) {
            binding.pbLoading.isVisible = it
        }
    }
}
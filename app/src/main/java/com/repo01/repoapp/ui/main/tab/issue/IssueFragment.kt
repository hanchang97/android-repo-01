package com.repo01.repoapp.ui.main.tab.issue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.repo01.repoapp.R
import com.repo01.repoapp.databinding.FragmentIssueBinding

class IssueFragment : Fragment() {

    private lateinit var binding: FragmentIssueBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_issue, container, false)
        return binding.root
    }
}
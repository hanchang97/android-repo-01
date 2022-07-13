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
    private var filterBarActivate = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_issue, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilterBar()
    }

    private fun setFilterBar(){
        binding.clFilterBar.setOnClickListener {
            filterBarActivate = !filterBarActivate
            if(filterBarActivate){
                binding.clFilterBar.setBackgroundResource(R.drawable.bg_issue_filter_bar_pressed)
            }
            else{
                binding.clFilterBar.setBackgroundResource(R.drawable.bg_issue_filter_bar_default)
            }
        }
    }
    // selector 를 통해 구현 시도 -> 실패
    // 백그라운드 리소스 교체로 임시 해결

}
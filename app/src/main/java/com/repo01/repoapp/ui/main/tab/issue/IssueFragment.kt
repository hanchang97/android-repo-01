package com.repo01.repoapp.ui.main.tab.issue

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
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
                binding.ivOption.setImageResource(R.drawable.ic_variant16_up)
                showMenu(it, R.menu.menu_issue_filter)
            }
            else{
                binding.clFilterBar.setBackgroundResource(R.drawable.bg_issue_filter_bar_default)
                binding.ivOption.setImageResource(R.drawable.ic_variant16)
            }

        }
    }
    // selector 를 통해 구현 시도 -> 실패
    // 백그라운드 리소스 교체로 임시 해결

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem ->
            Log.d("AppTest", "${menuItem}")
            true
        }

        popup.setOnDismissListener {
            // Respond to popup being dismissed.
            binding.clFilterBar.setBackgroundResource(R.drawable.bg_issue_filter_bar_default)
            binding.ivOption.setImageResource(R.drawable.ic_variant16)
            filterBarActivate = false
        }

        popup.menu.getItem(0).title = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(
                "<font color='#FFFFFF'>${getString(R.string.issue_menu_open)}</font>",
                Html.FROM_HTML_MODE_LEGACY
            )
        else
            Html.fromHtml("<font color='#FFFFFF'>${getString(R.string.issue_menu_open)}</font>")

        ///
        popup.menu.getItem(1).title = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(
                "<font color='#74869B'>${getString(R.string.issue_menu_closed)}</font>",
                Html.FROM_HTML_MODE_LEGACY
            )
        else
            Html.fromHtml("<font color='#74869B'>${getString(R.string.issue_menu_closed)}</font>")

        ///
        popup.menu.getItem(2).title = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(
                "<font color='#74869B'>${getString(R.string.issue_menu_all)}</font>",
                Html.FROM_HTML_MODE_LEGACY
            )
        else
            Html.fromHtml("<font color='#74869B'>${getString(R.string.issue_menu_all)}</font>")


        // Show the popup menu.
        popup.gravity = Gravity.END
        popup.show()
    }
    // < 보완 사항 >
    // 팝업메뉴 & 필터바 사이 여백 문제
    // 팝업 메뉴 전체 크기 조절 가능한지
    // 선택/미선택 메뉴의 텍스트 색상 설정 로직 짜기
}
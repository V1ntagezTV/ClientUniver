package com.example.testings.ui.shedules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.testings.R
import com.google.android.material.tabs.TabLayout

class SheduleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_shedule, container, false)

        val adapter = ShedulePageAdapter(childFragmentManager)
        val tabLayout = root.findViewById<TabLayout>(R.id.shedule_tab_layout)
        val viewPager = root.findViewById<ViewPager>(R.id.shedule_viewPager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        return root
    }
}
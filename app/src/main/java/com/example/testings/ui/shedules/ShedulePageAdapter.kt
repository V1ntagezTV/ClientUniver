package com.example.testings.ui.shedules

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.testings.ui.shedules.StudentShedulePage.StudentSheduleFragment
import com.example.testings.ui.shedules.TeacherShedulePage.TeacherSheduleFragment

open class ShedulePageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var PAGE_COUNT: Int = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> StudentSheduleFragment()
            1 -> TeacherSheduleFragment()
            else -> StudentSheduleFragment()
        }
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Студент"
            1 -> "Учитель"
            else -> ""
        }
    }
}
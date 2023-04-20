package com.example.mytodolistsingleactivity.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mytodolistsingleactivity.ui.DoingFragment
import com.example.mytodolistsingleactivity.ui.DoneFragment
import com.example.mytodolistsingleactivity.ui.ToDoFragment

class ViewPagerAdaptor(fragment:Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> {
                ToDoFragment()
            }
            1 -> {
                DoingFragment()
            }
            2 -> {
                DoneFragment()
            } else-> {
                ToDoFragment()
            }
        }

    }
}
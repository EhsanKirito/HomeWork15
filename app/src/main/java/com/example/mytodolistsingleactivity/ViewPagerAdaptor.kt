package com.example.mytodolistsingleactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

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
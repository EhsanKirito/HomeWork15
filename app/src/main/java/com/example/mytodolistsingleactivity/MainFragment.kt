package com.example.mytodolistsingleactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.mytodolist.ToDoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel : ToDoViewModel by activityViewModels()
    private val lableList:ArrayList<String> = arrayListOf("ToDo", "Doing", "Done")
    lateinit var adaptor: ViewPagerAdaptor
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerInit()
        buttonListener()

    }
    fun pagerInit(){
        adaptor = ViewPagerAdaptor(this)
        val viewPager2 = view?.findViewById<ViewPager2>(R.id.myViewPager2)

        viewPager2?.adapter = adaptor
        val tabLayout = view?.findViewById<TabLayout>(R.id.tabLayout)

        if (tabLayout != null) {
            if (viewPager2 != null) {
                TabLayoutMediator(
                    tabLayout,
                    viewPager2
                ) { tab, position -> tab.text = lableList[position] }.attach()
            }
        }
    }
    fun buttonListener(){
        view?.findViewById<FloatingActionButton>(R.id.floatingActionButton)?.setOnClickListener {
            viewModel.numberOfTasks.value = viewModel.numberOfTasks.value?.plus(1)
        }
    }
}
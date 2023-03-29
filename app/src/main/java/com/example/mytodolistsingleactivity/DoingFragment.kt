package com.example.mytodolistsingleactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.TaskColor
import com.example.mytodolist.ToDoViewModel
import com.example.mytodolistsingleactivity.databinding.FragmentDoingBinding
import com.example.mytodolistsingleactivity.databinding.FragmentToDoBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class DoingFragment : Fragment(R.layout.fragment_doing) {
    private lateinit var binding : FragmentDoingBinding
    private val viewModel : ToDoViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        recyclerViewInitiator()
        buttonListener()
    }

    fun buttonListener(){
        view?.findViewById<FloatingActionButton>(R.id.floatingActionButton)?.setOnClickListener {
            viewModel.numberOfTasks.value = viewModel.numberOfTasks.value?.plus(1)
        }
    }

    fun recyclerViewInitiator(){
        viewModel.makeTasks()
        val recyclerView: RecyclerView = binding.doingRecyclerView
        recyclerView.layoutManager = GridLayoutManager(view?.context, 2)
        val adaptor = ToDoAdaptor2(detail = { todoTask ->
            if (todoTask.taskColors == TaskColor.Blue){
                todoTask.taskColors = TaskColor.Red
            } else {
                todoTask.taskColors = TaskColor.Blue
            }
            viewModel.makeTasks()
        })
        adaptor.submitList(viewModel.taskListDoing)
        recyclerView.adapter = adaptor



        viewModel.numberOfTasks.observe(viewLifecycleOwner, Observer {
            viewModel.makeTasks()
            recyclerView.adapter = adaptor
            if (viewModel.taskListDoing.isEmpty()){
                binding.imageView.setImageResource(R.drawable.ic_launcher_background)
            } else {
                binding.imageView.setImageResource(R.drawable.ic_launcher_foreground)
            }
        })
    }
}
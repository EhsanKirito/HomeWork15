package com.example.mytodolistsingleactivity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.ToDoViewModel
import com.example.mytodolistsingleactivity.databinding.FragmentInputBinding


class InputFragment : Fragment(R.layout.fragment_input) {
    private val viewModel : ToDoViewModel by activityViewModels()
    private lateinit var binding: FragmentInputBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!



        binding.button.setOnClickListener {

                viewModel.username = binding.txtName.text.toString()
            val i = binding.txtNumberOfTasks.text.toString()
                viewModel.numberOfTasks.value = i.toInt()
                findNavController().navigate(R.id.action_inputFragment_to_toDoFragment)
        }


    }
}
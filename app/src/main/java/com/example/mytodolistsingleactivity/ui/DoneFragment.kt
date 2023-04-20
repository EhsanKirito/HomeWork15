package com.example.mytodolistsingleactivity.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.ToDoViewModel
import com.example.mytodolistsingleactivity.R
import com.example.mytodolistsingleactivity.ToDoAdaptor2
import com.example.mytodolistsingleactivity.data.TaskStatus
import com.example.mytodolistsingleactivity.databinding.FragmentDoneBinding

class DoneFragment : Fragment(R.layout.fragment_done) {
    private val viewModel : ToDoViewModel by activityViewModels()
    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewInitiator()
    }

    fun recyclerViewInitiator(){
        val recyclerView: RecyclerView = binding.doneRecyclerView
        recyclerView.layoutManager = GridLayoutManager(view?.context , 1)
        val adaptor = ToDoAdaptor2(
            detail = {
                it.isChecked = !it.isChecked

            }) { it, position ->
            val dialogView = layoutInflater.inflate(R.layout.dialogedit, null)
            val builder = AlertDialog.Builder(this.context)
                .setView(dialogView)
                .setTitle("Edit Task")
            val alertDialog = builder.show()
            var status: TaskStatus = TaskStatus.Todo
            var time = " "
            var date = " "
            val btnToDo = dialogView.findViewById<RadioButton>(R.id.ToDo)
            val btnDoing = dialogView.findViewById<RadioButton>(R.id.Doing)
            val btnDone = dialogView.findViewById<RadioButton>(R.id.Done)
            val txtName = dialogView.findViewById<EditText>(R.id.taskName)
            val txtDescription = dialogView.findViewById<EditText>(R.id.taskDescription)
            val btnDate = dialogView.findViewById<Button>(R.id.date)
            val btnTime = dialogView.findViewById<Button>(R.id.time)
            val btnDelete = dialogView.findViewById<Button>(R.id.delete)

            txtName.setText(viewModel.taskArray[viewModel.getPos(position, TaskStatus.Done)].taskName)
            txtDescription.setText(viewModel.taskArray[viewModel.getPos(position, TaskStatus.Done)].taskdescription)
            btnTime.text = viewModel.taskArray[viewModel.getPos(position, TaskStatus.Done)].taskTime
            btnDate.text = viewModel.taskArray[viewModel.getPos(position, TaskStatus.Done)].taskDate
            when(viewModel.taskArray[viewModel.getPos(position, TaskStatus.Done)].taskStatus){
                TaskStatus.Todo -> btnToDo.isChecked = true
                TaskStatus.Doing -> btnDoing.isChecked = true
                TaskStatus.Done -> btnDone.isChecked = true
            }

            btnToDo.setOnClickListener {
                btnDone.isChecked = false
                btnDoing.isChecked = false
                status = TaskStatus.Todo
            }
            btnDoing.setOnClickListener {
                btnDone.isChecked = false
                btnToDo.isChecked = false
                status = TaskStatus.Doing
            }
            btnDone.setOnClickListener {
                btnToDo.isChecked = false
                btnDoing.isChecked = false
                status = TaskStatus.Done
            }
            btnDate.setOnClickListener {
                val datePickerDialog = this.context?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        { _, year, month, dayOfMonth ->
                            date = "$year-${month + 1}-${dayOfMonth}"
                        },
                        2023,
                        1,
                        1
                    )
                }
                datePickerDialog?.show()
            }
            btnTime.setOnClickListener {
                val timePickerDialog = TimePickerDialog(
                    this.context,
                    { _, hourOfDay, minute ->

                        time = "$hourOfDay:$minute"
                    },
                    0,
                    0,
                    true
                )

                // Show the time picker dialog
                timePickerDialog.show()
            }


            val btnSave = dialogView.findViewById<Button>(R.id.Save).setOnClickListener {
                viewModel.editTask(
                    position,
                    TaskStatus.Done,
                    txtName.text.toString(),
                    txtDescription.text.toString(),
                    status,
                    time,
                    date
                )
                alertDialog.dismiss()
            }
            btnDelete.setOnClickListener {
                viewModel.deleteTask(viewModel.repository.get()[(viewModel.getPos(position, TaskStatus.Done))])
                alertDialog.dismiss()
            }
        }



        viewModel.taskListDone.observe(viewLifecycleOwner) {
            adaptor.submitList(viewModel.taskListDone.value)
            recyclerView.adapter = adaptor
            if (viewModel.taskListDone.value?.isEmpty() == true) {
                binding.imageView.setImageResource(R.drawable.ic_launcher_background)
            } else {
                binding.imageView.setImageResource(R.drawable.ic_launcher_foreground)
            }
        }
    }

}
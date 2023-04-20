package com.example.mytodolistsingleactivity.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.mytodolist.ToDoViewModel
import com.example.mytodolistsingleactivity.R
import com.example.mytodolistsingleactivity.adapter.ViewPagerAdaptor
import com.example.mytodolistsingleactivity.data.TaskStatus
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel: ToDoViewModel by activityViewModels()
    private val lableList: ArrayList<String> = arrayListOf("ToDo", "Doing", "Done")
    lateinit var adaptor: ViewPagerAdaptor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBarListener()
        pagerInit()
        buttonListener()

    }

    fun pagerInit() {
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

    fun buttonListener() {
        view?.findViewById<FloatingActionButton>(R.id.floatingActionButton)?.setOnClickListener {
            dialogListener()
        }
    }

    fun actionBarListener() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.actionbar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.delete -> {
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setMessage("Are you sure you want to delete the task?")
                            .setPositiveButton("yes") { _, _ ->
                                viewModel.deleteTasks()
                            }
                            .setNegativeButton(
                                "cancel",
                                null
                            )
                        builder.create()
                        builder.show()
                        true
                    }
                    R.id.signout -> {
                        activity?.startActivity(Intent(activity, SignInActivity::class.java))
                        true
                    }
                    R.id.deleteAll -> {
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setMessage("Are you sure you want to delete all tasks?")
                            .setPositiveButton("yes") { _, _ ->
                                viewModel.deleteAll()
                            }
                            .setNegativeButton(
                                "cancel",
                                null
                            )
                        builder.create()
                        builder.show()
                        true
                    }
                    else -> false
                }
            }
        })
    }

    fun dialogListener() {
        val dialogView = layoutInflater.inflate(R.layout.dialog, null)
        val builder = AlertDialog.Builder(this.context)
            .setView(dialogView)
            .setTitle("New Task")
        val alertDialog = builder.show()
        var status: TaskStatus = TaskStatus.Todo
        var time = " "
        var date = " "
        val btnToDo = dialogView.findViewById<RadioButton>(R.id.ToDo)
        btnToDo.isChecked = true
        val btnDoing = dialogView.findViewById<RadioButton>(R.id.Doing)
        val btnDone = dialogView.findViewById<RadioButton>(R.id.Done)
        val txtName = dialogView.findViewById<EditText>(R.id.taskName)
        val txtDescription = dialogView.findViewById<EditText>(R.id.taskDescription)
        val btnDate = dialogView.findViewById<Button>(R.id.date)
        val btnTime = dialogView.findViewById<Button>(R.id.time)
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


        dialogView.findViewById<Button>(R.id.Save).setOnClickListener {
            if (txtName.text.toString().isEmpty()) {
                Toast.makeText(context, "Subject can't be blank", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.addTask(
                    txtName.text.toString(),
                    txtDescription.text.toString(),
                    status,
                    time,
                    date
                )
                alertDialog.dismiss()
            }
        }
    }
}
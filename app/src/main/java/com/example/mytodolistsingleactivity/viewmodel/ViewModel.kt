package com.example.mytodolist


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.mytodolistsingleactivity.data.Repository

import com.example.mytodolistsingleactivity.data.TaskStatus
import com.example.mytodolistsingleactivity.data.ToDoTasks
import com.example.mytodolistsingleactivity.di.ServiceLocator


class ToDoViewModel() : ViewModel() {

    var username: String = " "
    val repository: Repository = ServiceLocator.provideRepository()
    val taskArray = repository.get()
    private val _taskList = MutableLiveData<ArrayList<ToDoTasks>>()
    val taskList: LiveData<ArrayList<ToDoTasks>> = _taskList

    val taskArrayTodo = ArrayList<ToDoTasks>()
    private val _taskListToDo = MutableLiveData<ArrayList<ToDoTasks>>()
    val taskListToDo: LiveData<ArrayList<ToDoTasks>> = _taskListToDo

    val taskArrayDoing = ArrayList<ToDoTasks>()
    private val _taskListDoing = MutableLiveData<ArrayList<ToDoTasks>>()
    val taskListDoing: LiveData<ArrayList<ToDoTasks>> = _taskListDoing

    val taskArrayDone = ArrayList<ToDoTasks>()
    val _taskListDone = MutableLiveData<ArrayList<ToDoTasks>>()
    val taskListDone: LiveData<ArrayList<ToDoTasks>> = _taskListDone


    fun addTask(
        taskName: String,
        taskDescription: String,
        taskStatus: TaskStatus,
        taskTime: String,
        taskDate: String
    ) {
        val newTask = ToDoTasks(taskName, taskDescription, taskStatus, taskTime, taskDate)

        repository.add(newTask)
        _taskList.value = repository.get()
        when (newTask.taskStatus) {

            TaskStatus.Todo -> {
                taskArrayTodo.add(newTask)
                _taskListToDo.value = taskArrayTodo
            }
            TaskStatus.Doing -> {
                taskArrayDoing.add(newTask)
                _taskListDoing.value = taskArrayDoing
            }
            TaskStatus.Done -> {
                taskArrayDone.add(newTask)
                _taskListDone.value = taskArrayDone
            }
        }
    }

    fun updateList() {
        taskArrayTodo.removeAll(taskArrayTodo)
        taskArrayDoing.removeAll(taskArrayDoing)
        taskArrayDone.removeAll(taskArrayDone)
        for (task in repository.get())
            when (task.taskStatus) {
                TaskStatus.Todo -> {
                    taskArrayTodo.add(task)
                    _taskListToDo.value = taskArrayTodo
                }
                TaskStatus.Doing -> {
                    taskArrayDoing.add(task)
                    _taskListDoing.value = taskArrayDoing
                }
                TaskStatus.Done -> {
                    taskArrayDone.add(task)
                    _taskListDone.value = taskArrayDone
                }
            }
    }

    fun getPos(position: Int, oldStatus: TaskStatus):Int{
        var newPos = 0
        if (oldStatus==TaskStatus.Todo){
            newPos = repository.get().indexOf(taskArrayTodo[position])
        }else if (oldStatus == TaskStatus.Doing){
            newPos = repository.get().indexOf(taskArrayDoing[position])
        }else {
            newPos = repository.get().indexOf(taskArrayDone[position])
        }
        return newPos
    }
    fun editTask(position: Int,oldStatus: TaskStatus, txtName:String, txtDescription:String, status: TaskStatus, time:String, date:String) {
        var newPos = getPos(position, oldStatus)
        repository.get()[newPos].apply {
            taskName = txtName
            taskdescription = txtDescription
            taskDate = date
            taskTime = time
            taskStatus = status
        }
        updateList()
    }

    fun deleteTask(task:ToDoTasks){
        repository.delete(task)
        updateList()
    }

    fun deleteTasks() {
        repository.deleteSelected()
        updateList()
    }

    fun deleteAll(){
        repository.deleteAll()
        updateList()
    }


}


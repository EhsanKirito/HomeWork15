package com.example.mytodolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class TaskStatus{
    Todo, Doing, Done
}
enum class TaskColor{
    Blue, Red
}

class ToDoTasks(val taskName:String) {
    var taskColors = TaskColor.Blue
    var n = 0
    fun randomStatus():TaskStatus{
        when ((1..3).random()){
            1 -> return TaskStatus.Todo
            2 -> return TaskStatus.Doing
            3 -> return TaskStatus.Done
        }
        n = 1
        return TaskStatus.Done
    }

    val taskStatus = randomStatus()

}

class ToDoViewModel : ViewModel(){
    var username:String= "username"
    var numberOfTasks = MutableLiveData<Int>(1)

    val taskList = ArrayList<ToDoTasks>()
    val taskListToDo = ArrayList<ToDoTasks>()
    val taskListDoing = ArrayList<ToDoTasks>()
    val taskListDone = ArrayList<ToDoTasks>()

    fun makeTasks(){
        for (i in taskList.size+1 ..numberOfTasks.value!!){
            val p = ToDoTasks("Task Number ${taskList.size+1}")
            if ((taskList.size+1)%2==0){
                p.taskColors = TaskColor.Red
            }
            taskList.add(p)

            when (p.taskStatus){
                TaskStatus.Todo -> {
                    taskListToDo.add(p)
                }
                TaskStatus.Doing -> {
                    taskListDoing.add(p)
                }
                TaskStatus.Done -> {
                    taskListDone.add(p)
                }
            }
            }

        }
    }


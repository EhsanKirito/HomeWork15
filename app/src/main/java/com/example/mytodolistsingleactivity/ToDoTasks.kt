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
    fun randomStatus():TaskStatus{
        when ((1..3).random()){
            1 -> return TaskStatus.Todo
            2 -> return TaskStatus.Doing
            3 -> return TaskStatus.Done
        }
        return TaskStatus.Done
    }
    val taskStatus = randomStatus()

}

class ToDoViewModel : ViewModel(){
    var username:String= "username"
    var numberOfTasks = MutableLiveData<Int>(1)

    val taskList = ArrayList<ToDoTasks>()

    fun makeTasks(){
        taskList.clear()
        for(i in 1..(numberOfTasks.value?.toInt()!!)){
            val p = ToDoTasks("Task Number $i")
            if (i%2==0){
                p.taskColors = TaskColor.Red
            }
            taskList.add(p)
        }
    }

}
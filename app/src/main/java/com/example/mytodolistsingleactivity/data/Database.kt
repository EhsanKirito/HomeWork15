package com.example.mytodolistsingleactivity

import com.example.mytodolistsingleactivity.data.ToDoTasks

object Database {
    var id:Int = 0
    val taskArray = ArrayList<ToDoTasks>()
}

interface DataService {
    fun add(task: ToDoTasks)
    fun get():ArrayList<ToDoTasks>
    fun update(task: ToDoTasks)
    fun delete(task: ToDoTasks)
    fun deleteAll()
    fun deleteSelected()
}

class DatabaseImp:DataService{
    override fun add(task: ToDoTasks) {
        task.id = Database.id++
        Database.taskArray.add(task)
    }

    override fun get(): ArrayList<ToDoTasks> {
        return Database.taskArray
    }

    override fun update(task: ToDoTasks) {
        for (i in Database.taskArray.indices){
            if (Database.taskArray[i].id == task.id){
                Database.taskArray[i] = task
            }
        }
    }

    override fun delete(task: ToDoTasks) {
        Database.taskArray.remove(task)
    }

    override fun deleteAll() {
        Database.taskArray.clear()
        Database.id = 0
    }

    override fun deleteSelected() {
        val list = ArrayList<ToDoTasks>()
        for (i in 0 until Database.taskArray.size){
            if (Database.taskArray[i].isChecked) list.add(Database.taskArray[i])
        }
        Database.taskArray.removeAll(list)
    }

}
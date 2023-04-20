package com.example.mytodolistsingleactivity.data

import com.example.mytodolistsingleactivity.DataService

class Repository(private val dataService: DataService) {
    fun add(task: ToDoTasks){
        dataService.add(task)
    }
    fun get():ArrayList<ToDoTasks>{
        return dataService.get()
    }
    fun update(task: ToDoTasks){
        dataService.update(task)
    }
    fun delete(task: ToDoTasks){
        dataService.delete(task)
    }
    fun deleteAll(){
        dataService.deleteAll()
    }

    fun deleteSelected() {
        dataService.deleteSelected()
    }
}
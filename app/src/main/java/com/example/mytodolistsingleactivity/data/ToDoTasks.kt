package com.example.mytodolistsingleactivity.data

class ToDoTasks(
    var taskName:String,
    var taskdescription:String,
    var taskStatus:TaskStatus,
    var taskDate:String,
    var taskTime:String,
    var id:Int = 0
){

    var isChecked:Boolean = false
}
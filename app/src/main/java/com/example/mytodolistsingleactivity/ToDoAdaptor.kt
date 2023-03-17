package com.example.mytodolistsingleactivity

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.TaskColor
import com.example.mytodolist.ToDoTasks

//class ToDoAdaptor(private val taskList:ArrayList<ToDoTasks>):RecyclerView.Adapter<ToDoAdaptor.ViewHolder>() {
//    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        val taskName: TextView = itemView.findViewById(R.id.txtTaskName)
//        val taskStatus: TextView = itemView.findViewById(R.id.txtTaskStatus)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv,parent,false))
//    }
//
//    override fun getItemCount(): Int {
//        return taskList.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.taskName.text = taskList[position].taskName
//        holder.taskStatus.text = taskList[position].taskStatus.toString()
//        holder.taskName.setBackgroundColor(
//            if (taskList[position].taskColors == TaskColor.Blue) Color.BLUE
//            else Color.RED
//        )
//
//        holder.taskStatus.setBackgroundColor(
//            if (taskList[position].taskColors == TaskColor.Blue) Color.BLUE
//            else Color.RED
//        )
//
//    }
//}

class ToDoAdaptor2(private val detail: (ToDoTasks)->Unit):
    ListAdapter<ToDoTasks, ToDoAdaptor2.ToDoViewHolder>(ToDoCallBack()){
    class ToDoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val taskName: TextView = itemView.findViewById(R.id.txtTaskName)
        val taskStatus: TextView = itemView.findViewById(R.id.txtTaskStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv,parent,false)
        return ToDoAdaptor2.ToDoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val item = getItem(position)
        holder.taskName.text = item.taskName
        holder.taskStatus.text = item.taskStatus.toString()
        holder.taskName.setBackgroundColor(
            if (item.taskColors == TaskColor.Blue) Color.BLUE
            else Color.RED
        )
        holder.taskStatus.setBackgroundColor(
            if (item.taskColors == TaskColor.Blue) Color.BLUE
            else Color.RED
        )
        holder.taskName.setOnLongClickListener {
            detail(item)
            notifyDataSetChanged()
            return@setOnLongClickListener true
        }
    }


}
class ToDoCallBack: DiffUtil.ItemCallback<ToDoTasks>(){
    override fun areItemsTheSame(oldItem: ToDoTasks, newItem: ToDoTasks): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ToDoTasks, newItem: ToDoTasks): Boolean {
        return oldItem.taskName == newItem.taskName
    }

}
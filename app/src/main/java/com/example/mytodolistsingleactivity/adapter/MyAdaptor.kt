package com.example.mytodolistsingleactivity

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolistsingleactivity.data.TaskColor
import com.example.mytodolistsingleactivity.data.ToDoTasks


class ToDoAdaptor2(
    private val detail: (ToDoTasks)->Unit,
    private val detail2: (ToDoTasks, position:Int) -> Unit
):
    ListAdapter<ToDoTasks, ToDoAdaptor2.ToDoViewHolder>(ToDoCallBack()){
    inner class ToDoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var pos:Int = 0
        val taskName: TextView = itemView.findViewById(R.id.txtTaskName)
        val taskDescription: TextView = itemView.findViewById(R.id.txtTaskDescription)
        val taskTime: TextView = itemView.findViewById(R.id.time)
        val taskDate: TextView = itemView.findViewById(R.id.date)
        val btnCheck: Button = itemView.findViewById(R.id.checked)
        val itemClicker: ConstraintLayout = itemView.findViewById(R.id.itemClicker)

        init {
            itemClicker.setOnClickListener {
                pos = adapterPosition
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoAdaptor2.ToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv,parent,false)
        return ToDoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoAdaptor2.ToDoViewHolder, position: Int) {
        val item = getItem(position)
        holder.taskName.text = item.taskName
        holder.taskDescription.text = item.taskdescription
        holder.taskTime.text = item.taskTime
        holder.taskDate.text = item.taskDate
        holder.btnCheck.text = item.taskName[0].toString()
        holder.itemClicker.setOnClickListener {
            detail2(item,position)
        }

        holder.itemClicker.setOnLongClickListener {
            detail(item)
            holder.itemClicker.setBackgroundColor(
                if (!item.isChecked) Color.WHITE
                else Color.YELLOW
            )
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

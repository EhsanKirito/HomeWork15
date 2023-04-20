package com.example.mytodolistsingleactivity.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mytodolistsingleactivity.R

class ToDoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
    }

    companion object {
        private val tag: String by lazy {
            ToDoActivity::class.java.name
        }
        fun signInIntent(context: Context): Intent {
            return Intent(context, ToDoActivity::class.java)
        }
    }

}
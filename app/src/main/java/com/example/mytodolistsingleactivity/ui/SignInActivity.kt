package com.example.mytodolistsingleactivity.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mytodolistsingleactivity.data.LoginData
import com.example.mytodolistsingleactivity.databinding.ActivitySignInBinding
import com.google.android.material.snackbar.Snackbar

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var acSignUp: ActivityResultLauncher<Intent>
    private lateinit var acSignIn: ActivityResultLauncher<Intent>
    private val infoSaver = LoginData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        acSignUp = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK) {
                binding.txtUsernameIn.setText(it.data?.extras?.getString("NewUserUp") ?: "NULL")
                binding.txtPasswordIn.setText(it.data?.extras?.getString("NewPassUp") ?: "NULL")
            } else   Toast.makeText(this, "Result not found", Toast.LENGTH_SHORT).show()
        }

        acSignIn = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK) {
            } else   Toast.makeText(this, "Login Failed. Try again.", Toast.LENGTH_SHORT).show()
        }

        binding.btnSignIn.setOnClickListener {
            if (binding.txtUsernameIn.text.toString().isEmpty())
                Toast.makeText(this, "Please enter a Username", Toast.LENGTH_SHORT).show()
            else if (binding.txtPasswordIn.text.toString().isEmpty())
                Toast.makeText(this, "Please enter the Password", Toast.LENGTH_SHORT).show()
            else {
                if(infoSaver.checkLogin(binding.txtUsernameIn.text.toString(), binding.txtPasswordIn.text.toString())) {
                    Snackbar.make(it, "Login was Successful", Snackbar.LENGTH_SHORT).show()
                    acSignIn.launch(ToDoActivity.signInIntent(this))
                } else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }




        binding.btnSignUp.setOnClickListener {
            acSignUp.launch(SignUpActivity.signUpIntent(this, binding.txtUsernameIn.text.toString(), binding.txtPasswordIn.text.toString()))
        }

    }
}
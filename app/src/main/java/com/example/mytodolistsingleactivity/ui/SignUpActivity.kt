package com.example.mytodolistsingleactivity.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mytodolistsingleactivity.data.LoginData
import com.example.mytodolistsingleactivity.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val infoSaver = LoginData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtUsernameUp.setText((intent?.getStringExtra("userReturn")))
        binding.txtPasswordUp.setText((intent?.getStringExtra("passReturn")))

        binding.btnSignUp.setOnClickListener {
            if (binding.txtUsernameUp.text.toString().isEmpty())
                Toast.makeText(this, "Username can't be blank", Toast.LENGTH_SHORT).show()
            else if (binding.txtPasswordUp.text.toString().isEmpty())
                Toast.makeText(this, "Password can't be blank", Toast.LENGTH_SHORT).show()
            else {

                if (infoSaver.registerUser(
                        binding.txtUsernameUp.text.toString(),
                        binding.txtPasswordUp.text.toString()
                    )
                ) {
                    Toast.makeText(this, "Register successfully done", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
                }

            }
        }


    }

    companion object {
        private val tag: String by lazy {
            SignUpActivity::class.java.name
        }

        fun signUpIntent(context: Context, user: String, pass: String): Intent {
            return Intent(context, SignUpActivity::class.java).apply {
                putExtra("userReturn", user)
                putExtra("passReturn", pass)
            }
        }
    }

    override fun onBackPressed() {
        val signUpIntent = Intent().apply {
            putExtra("NewUserUp", binding.txtUsernameUp.text.toString())
            putExtra("NewPassUp", binding.txtPasswordUp.text.toString())
        }
        setResult(RESULT_OK, signUpIntent)
        super.onBackPressed()

    }
}
package com.example.eaapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ForgotPassActivity : AppCompatActivity() {
    private lateinit var email : EditText
    private lateinit var recoverButton: Button
    private lateinit var signIn: TextView
    private lateinit var signUp: TextView
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpass)
        mAuth =  FirebaseAuth.getInstance()
        signIn = findViewById(R.id.signInText)
        signUp = findViewById(R.id.signUpText)

        signIn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }
        signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent);
        }
        email = findViewById(R.id.emailET)

        recoverButton = findViewById(R.id.recoverButton)
        recoverButton.setOnClickListener{
            ResetPassword(email.text.toString().trim())
        }
    }

    private fun ResetPassword(emailText: String){
        if(!emailText.isNotEmpty()){
            email.error = "Email is required"
            email.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            email.error = "Please input valid email"
            email.requestFocus()
            return
        }
        mAuth?.sendPasswordResetEmail(emailText)
            ?.addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Recover instruction is sent. Check your email please! ", Toast.LENGTH_LONG).show()
                }
                if (!task.isSuccessful){
                    Toast.makeText(this, "Woops! Something went wrong... Or User with this email not found...", Toast.LENGTH_LONG).show()
                }
            }
    }
}
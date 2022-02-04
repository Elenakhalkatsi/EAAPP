package com.example.eaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private lateinit var submit: Button
    private lateinit var email: EditText
    private lateinit var passwrod: EditText
    private lateinit var signup: TextView
    private lateinit var forgotPass: TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()

        email  = findViewById(R.id.email)
        passwrod  = findViewById(R.id.password)
        submit = findViewById(R.id.singInButton)
        submit.setOnClickListener{SignIn(email.text.toString().trim(), passwrod.text.toString().trim())}

        signup = findViewById(R.id.signUp)
        signup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent);
        }

        forgotPass = findViewById(R.id.forgotPass)
        forgotPass.setOnClickListener {
            val intent = Intent(this, ForgotPassActivity::class.java)
            startActivity(intent);
        }
    }

    private fun SignIn(emailText: String, passwordText : String ){
        if (TextUtils.isEmpty(emailText)){
            email.error = "Please input email";
        }
        else if(TextUtils.isEmpty(passwordText) ){
            passwrod.error = "Please input password, Password must contain 6 characters";
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            email.error="Input valid email";
        }
        else if(passwordText.length < 6){
            passwrod.error = "Password must includes min 6 characters";
        }
        else{
            mAuth?.signInWithEmailAndPassword(emailText,passwordText)
                ?.addOnSuccessListener {
                    val intent = Intent(this, SignedInAcctivity::class.java)
                    startActivity(intent);
                    Toast.makeText(this, "Log in successfully", Toast.LENGTH_SHORT).show()
                }
                ?.addOnFailureListener{
                        e -> Toast.makeText(this, "User not fount with this email or password is incorrect", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
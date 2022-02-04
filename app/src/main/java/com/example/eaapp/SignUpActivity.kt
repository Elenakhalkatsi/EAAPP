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
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private lateinit var email:EditText
    private lateinit var fName:EditText
    private lateinit var lName:EditText
    private lateinit var nName:EditText
    private lateinit var passwrod: EditText
    private lateinit var registerButton: Button
    private lateinit var signIn: TextView
    private lateinit var forgotPass: TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        mAuth = FirebaseAuth.getInstance()

        signIn = findViewById(R.id.signInText)
        signIn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }

        forgotPass = findViewById(R.id.forgotPass)
        forgotPass.setOnClickListener {
            val intent = Intent(this, ForgotPassActivity::class.java)
            startActivity(intent);
        }
        email = findViewById(R.id.email)
        fName = findViewById(R.id.firstName)
        lName = findViewById(R.id.lastName)
        nName = findViewById(R.id.nickName)
        passwrod = findViewById(R.id.password)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener{
            Register(email.text.toString().trim(),
                fName.text.toString().trim(),
                lName.text.toString().trim(),
                nName.text.toString().trim(),
                passwrod.text.toString().trim()
            )}
    }


    private fun Register(emailText: String,
                         fnameText: String,
                         lnameText: String,
                         nnameText: String,
                         passwordText: String){
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
        if(fnameText.isEmpty()){
            fName.error = "Please input first name"
            fName.requestFocus()
            return
        }
        if(lnameText.isEmpty()){
            lName.error = "Please input last name"
            lName.requestFocus()
            return
        }
        if(passwordText.isEmpty()){
            passwrod.error = "Please input password"
            passwrod.requestFocus()
            return
        }
        else if(passwordText.length < 6){
            passwrod.error = "Password must includes min 6 characters";
        }
        mAuth?.createUserWithEmailAndPassword(emailText,passwordText)
            ?.addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    val user = User(emailText,fnameText,lnameText,nnameText)
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(mAuth?.currentUser!!.uid)
                        .setValue(user)
                        .addOnCompleteListener{ completedTask ->
                            if (completedTask.isSuccessful){
                                val intent = Intent(this, SignedInAcctivity::class.java)
                                startActivity(intent);
                                Toast.makeText(this, "Registration completed successfully", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
                else{
                    Toast.makeText(this, "User with this email already exists", Toast.LENGTH_SHORT).show()

                }

            }
    }
}
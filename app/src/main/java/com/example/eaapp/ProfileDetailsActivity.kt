package com.example.eaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileDetailsActivity : AppCompatActivity() {

    private lateinit var user: FirebaseUser
    private lateinit var userId : String
    private lateinit var reference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var fnameSh: TextView
    private lateinit var lnameSh: TextView
    private lateinit var nnameSh: TextView
    private lateinit var emailSh: TextView
    private lateinit var mainpage: Button
    private lateinit var logout: Button
    private lateinit var showlocation: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiledetails)
        mAuth = FirebaseAuth.getInstance()
        fnameSh = findViewById(R.id.firstNameshow)
        lnameSh = findViewById(R.id.lastNameshow)
        nnameSh = findViewById(R.id.nickNameshow)
        emailSh = findViewById(R.id.emailshow)
        mainpage = findViewById(R.id.mainPage)
        logout = findViewById(R.id.signOut)
        showlocation = findViewById(R.id.showLoc)

        logout.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }

        mainpage.setOnClickListener{
            val intent = Intent(this, SignedInAcctivity::class.java)
            startActivity(intent);
        }
        user = mAuth.currentUser!!;
        reference = Firebase.database.getReference("Users")
        userId = user.uid
        reference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userInfo = snapshot.getValue(User::class.java)
                if (userInfo != null){
                     fnameSh.text = userInfo.firstName
                     lnameSh.text = userInfo.lastName
                     nnameSh.text = userInfo.nickName
                     emailSh.text = userInfo.email
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileDetailsActivity, "User data has not found!", Toast.LENGTH_LONG).show()
            }
        })
    }
}
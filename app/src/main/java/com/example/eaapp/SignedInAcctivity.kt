package com.example.eaapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eaapp.retrofit.Client
import com.example.eaapp.retrofit.Movie
import com.example.eaapp.retrofit.MovieAdapter
import com.example.eaapp.retrofit.MoviesResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignedInAcctivity : AppCompatActivity() {
    private lateinit var user: FirebaseUser
    private lateinit var userId : String
    private lateinit var reference: DatabaseReference
    private lateinit var signOut: Button
    private lateinit var profile: Button
    private lateinit var mAuth: FirebaseAuth
    var movieList = ArrayList<Movie>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signedin)
        recyclerView = findViewById(R.id.RecyclerViewId)
        movieList = ArrayList()
        recyclerView.layoutManager = GridLayoutManager(this,2)
        recyclerView.itemAnimator = DefaultItemAnimator()
        mAuth =  FirebaseAuth.getInstance()
        user = mAuth.currentUser!!;
        signOut = findViewById(R.id.signOut)
        signOut.setOnClickListener{
            mAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }
        loadJSON()

        profile = findViewById(R.id.profile)
        profile.setOnClickListener{
            val intent = Intent(this, ProfileDetailsActivity::class.java)
            startActivity(intent);
        }
    }

    private fun loadJSON(){
        try {
            val apiService = Client.create()
            val call: Call<MoviesResponse> = apiService.getPopularMovies("55a8dfa73a91452d885adbfb9e96c5ae")

            call.enqueue(object : Callback<MoviesResponse> {
                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG).show()
                    t.printStackTrace()                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                    if (response.isSuccessful) {
                        movieList = response.body()!!.results
                        val movieAdapter = MovieAdapter(applicationContext,movieList)
                        recyclerView.adapter = movieAdapter
                        movieAdapter.notifyDataSetChanged()
                    }
                }
            })
        }catch (e: Exception){
            Toast.makeText(this, "This app  has no data", Toast.LENGTH_LONG).show()
        }
    }
}
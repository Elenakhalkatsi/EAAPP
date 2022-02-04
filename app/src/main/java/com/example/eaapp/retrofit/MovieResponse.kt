package com.example.eaapp.retrofit

import com.google.gson.annotations.SerializedName


data class MoviesResponse (
    @SerializedName("results")
    val results: ArrayList<Movie>
)
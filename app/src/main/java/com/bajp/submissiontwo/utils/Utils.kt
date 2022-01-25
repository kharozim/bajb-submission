package com.bajp.submissiontwo.utils

import android.app.Activity
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment

const val BASE_URL_API = "https://api.themoviedb.org/3/"

fun Activity.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(message: String){
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}
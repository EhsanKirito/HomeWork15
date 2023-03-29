package com.example.homework15_3

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadImageUrl")
fun ImageView.loadImageUrl(url:String){
    Glide.with(this.context).load(url).placeholder(R.drawable.loading_animation).into(this)
}
package com.feyzaurkut.movieapp.util

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.squareup.picasso.Picasso

fun AppCompatImageView.getPhoto(url: String) {
    Picasso.get().load(url).into(this);
}

inline fun View.setOnDoubleClickListener(
    crossinline onDoubleClick: (View) -> Unit
) {
    setOnClickListener(object : DoubleClickListener() {
        override fun onDoubleClick(v: View) {
            onDoubleClick(v)
        }
    })
}
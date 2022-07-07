package com.feyzaurkut.movieapp.util

import androidx.appcompat.widget.AppCompatImageView
import com.squareup.picasso.Picasso

fun AppCompatImageView.getPhoto(url: String) {
    Picasso.get().load(url).into(this);
}
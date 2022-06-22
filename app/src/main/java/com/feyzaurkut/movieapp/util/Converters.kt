package com.feyzaurkut.movieapp.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object Converters {
    @TypeConverter
    fun fromString(value: String): ArrayList<Int> {
        val type = object: TypeToken<ArrayList<Int>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Int>): String {
        val type = object: TypeToken<ArrayList<Int>>() {}.type
        return Gson().toJson(list, type)
    }
}
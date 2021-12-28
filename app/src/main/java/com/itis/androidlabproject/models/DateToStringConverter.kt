package com.itis.androidlabproject.models

import java.text.SimpleDateFormat
import java.util.*

object DateToStringConverter {

    fun convertDateToString(date: Date?): String{
        val dateFormat = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())
        return date.let {
            dateFormat.format(it)
        } ?: "Дата не выбрана"
    }
}

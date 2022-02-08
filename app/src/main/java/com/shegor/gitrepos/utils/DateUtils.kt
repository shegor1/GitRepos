package com.shegor.gitrepos.utils

import java.time.format.DateTimeFormatter

object DateUtils {

    private val jsonDateFormatter: DateTimeFormatter by lazy {
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    }

    private val dateFormatter: DateTimeFormatter by lazy {
        DateTimeFormatter.ofPattern("dd.MM.yyyy")
    }

    fun formatJsonDate(jsonDate: String): String {
        val date = jsonDateFormatter.parse(jsonDate)
        return dateFormatter.format(date)

    }
}
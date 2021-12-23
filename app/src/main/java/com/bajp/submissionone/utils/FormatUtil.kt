package com.bajp.submissionone.utils

import java.text.ParseException
import java.text.SimpleDateFormat

object FormatUtil {
    fun formatDate(dateString: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val newDate =
            try {
                dateFormat.parse(dateString)
            } catch (e: ParseException) {
                null
            }
        val newFormat = SimpleDateFormat("MMM dd, yyyy")
        val newDateFormat = newDate?.let { newFormat.format(it) }
        return newDateFormat ?: ""
    }
}
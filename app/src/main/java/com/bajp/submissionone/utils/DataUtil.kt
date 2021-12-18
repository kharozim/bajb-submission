package com.bajp.submissionone.utils

import android.content.Context
import android.util.Log
import com.bajp.submissionone.data.ContentEntity
import java.io.IOException
import java.io.InputStream

object DataUtil {
    fun generateDataMovie(context: Context): String? {
        val content = ContentEntity()
        var json: String? = null
        json = try {
            val input: InputStream = context.assets.open("movie_response.json")
            val size: Int = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun generateDataTV(context: Context): String? {
        val content = ContentEntity()
        var json: String? = null
        json = try {
            val input: InputStream = context.assets.open("tv_response.json")
            val size: Int = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        Log.e("TAG", "json : $json")
        return json
    }
}
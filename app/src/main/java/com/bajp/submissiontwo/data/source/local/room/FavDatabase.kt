package com.bajp.submissiontwo.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity

@Database(entities = [ContentItemEntity::class], version = 1, exportSchema = false)
abstract class FavDatabase : RoomDatabase() {

    abstract fun favDao(): FavDao

    companion object {
        @Volatile
        private var INSTANCE: FavDatabase? = null

        fun getInstance(context: Context): FavDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    FavDatabase::class.java,
                    "favorite.db"
                ).build().apply {
                    INSTANCE = this
                }
            }

    }
}
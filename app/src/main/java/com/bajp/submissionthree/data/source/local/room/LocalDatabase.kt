package com.bajp.submissionthree.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity

@Database(entities = [CatalogEntity::class, MovieEntitiy::class, TvShowEntity::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun favDao(): LocalDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "catalog.db"
                ).build().apply {
                    INSTANCE = this
                }
            }

    }
}
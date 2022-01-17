package com.bajp.submissiontwo.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity

@Dao
interface FavDao {
    @Query("SELECT * FROM content_item_entities WHERE is_movie=:isMovie")
    fun getMovieFav(isMovie: Boolean = true): LiveData<List<ContentItemEntity>>

    @Query("SELECT * FROM content_item_entities WHERE is_movie=:isMovie")
    fun getTvFav(isMovie: Boolean = false): LiveData<List<ContentItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFav(movie: ContentItemEntity)

    @Delete
    fun delete(movie: ContentItemEntity)
}
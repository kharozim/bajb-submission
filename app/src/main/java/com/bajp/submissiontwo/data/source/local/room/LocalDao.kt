package com.bajp.submissiontwo.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity
import androidx.paging.DataSource

@Dao
interface LocalDao {
    @Query("SELECT * FROM content_item_entities WHERE is_movie=1")
    fun getMovies(): DataSource.Factory<Int, ContentItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(items: List<ContentItemEntity>)

    @Query("SELECT * FROM content_item_entities WHERE is_movie=0")
    fun getTvs(): DataSource.Factory<Int, ContentItemEntity>

    @Query("SELECT * FROM content_item_entities WHERE is_movie =1 AND id=:id")
    fun getDetailMovie(id: Int): LiveData<ContentItemEntity>

    @Query("SELECT * FROM content_item_entities WHERE is_movie=0 AND id=:id")
    fun getDetailTv(id: Int): LiveData<ContentItemEntity>

    @Update
    fun setFav(movie: ContentItemEntity)

    @Query("SELECT * FROM content_item_entities WHERE is_Favorite=1 ORDER BY is_movie")
    fun getFavorite() : LiveData<List<ContentItemEntity>>
//
//    @Query("SELECT * FROM content_item_entities WHERE is_movie=0 AND is_Favorite=1")
//    fun getFavTv(movie: ContentItemEntity)
}
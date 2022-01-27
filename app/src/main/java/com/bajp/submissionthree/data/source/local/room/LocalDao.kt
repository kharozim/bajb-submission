package com.bajp.submissionthree.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity

@Dao
interface LocalDao {
    @Query("SELECT * FROM catalog_entity WHERE is_movie=1")
    fun getMovies(): DataSource.Factory<Int, CatalogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCatalog(items: List<CatalogEntity>)

    @Query("SELECT * FROM catalog_entity WHERE is_movie=0")
    fun getTvs(): DataSource.Factory<Int, CatalogEntity>

    @Query("SELECT * FROM catalog_entity WHERE is_movie =1 AND id=:id")
    fun getDetailMovie(id: Int): LiveData<CatalogEntity>

    @Query("SELECT * FROM catalog_entity WHERE is_movie=0 AND id=:id")
    fun getDetailTv(id: Int): LiveData<CatalogEntity>

    @Update
    fun setFav(movie: CatalogEntity)

    @Query("SELECT * FROM catalog_entity WHERE is_Favorite=1 ORDER BY is_movie")
    fun getFavorite() : LiveData<List<CatalogEntity>>

}
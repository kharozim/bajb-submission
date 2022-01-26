package com.bajp.submissionthree.data.source.local.entities

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "content_item_entities")
@Parcelize
data class ContentItemEntity(
    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "image_poster")
    var imagePoster: String,

    @ColumnInfo(name = "image_slider")
    var imageSlider: String,

    @ColumnInfo(name = "rating")
    val rating: Double,

    @ColumnInfo(name = "rating_count")
    var ratingCount: Int,


    @ColumnInfo(name = "release_date")
    var releaseDate: String,

    @ColumnInfo(name = "is_movie")
    var isMovie: Boolean,

    @ColumnInfo(name = "is_Favorite")
    var isFavorite: Boolean,
) : Parcelable

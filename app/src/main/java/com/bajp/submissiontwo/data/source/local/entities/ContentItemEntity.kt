package com.bajp.submissiontwo.data.source.local.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "content_item_entities")
data class ContentItemEntity(
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,

    @NonNull
    @ColumnInfo(name = "name")
    val name: String,

    @NonNull
    @ColumnInfo(name = "description")
    val description: String,

    @NonNull
    @ColumnInfo(name = "image_poster")
    var imagePoster: String,

    @NonNull
    @ColumnInfo(name = "image_slider")
    var imageSlider: String,

    @NonNull
    @ColumnInfo(name = "rating")
    val rating: Double,

    @NonNull
    @ColumnInfo(name = "rating_count")
    var ratingCount: Int,

    @NonNull
    @ColumnInfo(name = "release_date")
    var releaseDate: String,

    @NonNull
    @ColumnInfo(name = "is_movie")
    var isMovie: Boolean,
)

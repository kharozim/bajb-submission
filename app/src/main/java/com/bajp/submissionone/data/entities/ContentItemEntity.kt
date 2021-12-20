package com.bajp.submissionone.data.entities

import com.google.gson.annotations.SerializedName

data class ContentItemEntity(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("image_poster")
    var imagePoster: String,
    @SerializedName("image_slider")
    var imageSlider: String,
    val rating: Double,
    @SerializedName("rating_count")
    var ratingCount: Int,
    @SerializedName("release_date")
    var releaseDate: String,
)

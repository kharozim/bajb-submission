package com.bajp.submissionone.data.entities

import com.google.gson.annotations.SerializedName

data class ContentItemEntity(
    val id: Int = 0,
    val name: String = "",
    @SerializedName("image_poster")
    var imagePoster: String = "",
    @SerializedName("image_slider")
    var imageSlider: String = "",
    val rating: String = "",
    val description: String = ""
)

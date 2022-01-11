package com.bajp.submissiontwo.data.source.local.entities

data class ContentItemEntity(
    val id: Int,
    val name: String,
    val description: String,
    var imagePoster: String,
    var imageSlider: String,
    val rating: Double,
    var ratingCount: Int,
    var releaseDate: String,
)

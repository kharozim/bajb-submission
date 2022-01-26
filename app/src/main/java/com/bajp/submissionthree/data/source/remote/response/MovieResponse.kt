package com.bajp.submissionthree.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title", alternate = ["name"])
    val name: String? = null,

    @field:SerializedName("overview")
    val description: String? = null,

    @field:SerializedName("backdrop_path")
    var imageSlider: String? = null,

    @field:SerializedName("poster_path")
    var imagePoster: String? = null,

    @field:SerializedName("vote_average")
    val rating: Double? = null,

    @field:SerializedName("vote_count")
    val ratingCount: Int? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,
)
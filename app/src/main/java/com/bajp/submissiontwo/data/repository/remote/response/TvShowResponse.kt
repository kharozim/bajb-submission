package com.bajp.submissiontwo.data.repository.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowResponse(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
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

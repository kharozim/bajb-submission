package com.bajp.submissionone.data

import com.bajp.submissionone.data.entities.ContentItemEntity

data class ContentEntity(
    val results: List<ContentItemEntity> = emptyList()
)
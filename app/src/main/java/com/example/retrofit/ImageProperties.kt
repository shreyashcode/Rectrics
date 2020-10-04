package com.example.retrofit

import com.squareup.moshi.Json

data class ImageProperties
    (
    val id: Int,
    @Json(name = "url")val downLoadUrl: String,
    @Json(name = "download_url")val displayUrl: String,
    val author: String
)




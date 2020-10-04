package com.example.retrofit.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class ImageEntity(
    val isClicked: Int,
    val downLoadUrl: String,
    @PrimaryKey
    val displayUrl: String,
    val author: String
)
//{
//    @PrimaryKey(autoGenerate = true)
//    var id: Int = 0
//}
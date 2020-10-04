package com.example.retrofit.database

import androidx.room.*

@Dao
interface ImageDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imageEntity: ImageEntity)

    @Query("SELECT * FROM ImageEntity")
    suspend fun getAllFavourite(): List<ImageEntity>

    @Delete
    suspend fun delete(imageEntity: ImageEntity)
}
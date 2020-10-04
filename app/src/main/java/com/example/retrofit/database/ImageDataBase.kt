package com.example.retrofit.database

import android.content.Context
import android.media.Image
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ImageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ImageDataBase: RoomDatabase()
{
    abstract fun getImageDao(): ImageDao

    companion object
    {
        @Volatile
        private var INSTANCE: ImageDataBase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK)
        {
            INSTANCE ?:getDatabase(context).also{

                INSTANCE = it

            }
        }

        private fun getDatabase(context: Context) =
                Room.databaseBuilder(
                    context,
                    ImageDataBase::class.java,
                    "ImageEntity"
                ).build()
        }
    }
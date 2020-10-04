package com.example.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ImageViewModel : ViewModel()
{
    private val _imagesList = MutableLiveData<List<ImageProperties>>()

    val imageList: LiveData<List<ImageProperties>>
        get() = _imagesList

    var viewJob = Job()
    val coroutineScope = CoroutineScope(viewJob + Dispatchers.Main)

    fun getImageProperties()
    {
        coroutineScope.launch()
        {
            var getDeferredProperties = ImageApi.retrofitService.getProperties()
            try
            {
                var ListResult = getDeferredProperties.await()
                if(ListResult.size > 0)
                {
                    _imagesList.value = ListResult
                }
            }
            catch (e: Exception)
            {

            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewJob.cancel()
    }
}

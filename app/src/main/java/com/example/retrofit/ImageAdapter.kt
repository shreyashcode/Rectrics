package com.example.retrofit

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.database.ImageDataBase
import com.example.retrofit.database.ImageEntity
import com.example.retrofit.databinding.ItemViewBinding
import kotlinx.android.synthetic.main.item_view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ImageAdapter (
    private val list : List<ImageProperties>,
    private val recyclerViewClickListener: RecyclerViewClickListener,
    private val context: Context
): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_view,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.itemViewBinding.imagePro = list[position]
        val imageData = list[position]
        var isClicked = 0
        var job = Job()
        val coroutionScope = CoroutineScope(job + Dispatchers.Main)
        coroutionScope.launch()
        {
            val list = ImageDataBase(context).getImageDao().getAllFavourite()
            for(images in list)
            {
                if(images.displayUrl == imageData.displayUrl)
                {
                    holder.itemViewBinding.isLiked.setBackgroundResource(R.drawable.like)
                    isClicked = 1
                    break
                }
            }
        }
        if(isClicked == 0)
        {
            holder.itemViewBinding.isLiked.setBackgroundResource(R.drawable.dislike)
        }
        holder.itemViewBinding.imageView.setOnClickListener(View.OnClickListener {
            recyclerViewClickListener.RecyclerViewClickListenerMethod(list[position], it.id)
        })

        holder.itemViewBinding.isLiked.setOnClickListener(
            View.OnClickListener {
               getLikedOrDisliked(list[position], holder)
            })
    }

    private fun getLikedOrDisliked(imageProperties: ImageProperties, holder: ImageViewHolder)
    {
        var isDeleted = 0
        // Toast.makeText(this, "${imageData.author}", Toast.LENGTH_SHORT).show()
        var job = Job()
        // Log.d("mList", "imageViewData")
        val coroutionScope = CoroutineScope(job + Dispatchers.Main)
        coroutionScope.launch()
        {
            val list = ImageDataBase(context).getImageDao().getAllFavourite()
            for(images in list)
            {
                if (images.displayUrl.equals(imageProperties.displayUrl)) {
                    holder.itemViewBinding.isLiked.setBackgroundResource(R.drawable.dislike)
                    ImageDataBase(context).getImageDao().delete(images)
                    isDeleted = 1
                    break
                }
            }
            if(isDeleted == 0)
            {
                val image: ImageEntity = ImageEntity(1, imageProperties.downLoadUrl, imageProperties.displayUrl, imageProperties.author)
                ImageDataBase(context).getImageDao().insertImage(image)
                holder.itemViewBinding.isLiked.setBackgroundResource(R.drawable.like)
            }
        }
    }


    override fun getItemCount() = list.size

    inner class ImageViewHolder(
        val itemViewBinding : ItemViewBinding
    ): RecyclerView.ViewHolder(itemViewBinding.root)
}




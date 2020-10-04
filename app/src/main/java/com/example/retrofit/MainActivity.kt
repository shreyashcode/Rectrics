package com.example.retrofit

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.retrofit.database.ImageDataBase
import com.example.retrofit.database.ImageEntity
import com.google.android.material.snackbar.Snackbar
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), RecyclerViewClickListener
{
    private lateinit var imageViewModel: ImageViewModel
    private lateinit var context: Context

    var job = Job()
    val coroutionScope = CoroutineScope(job + Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener{

            val intent = Intent(this, FavouritesActivity::class.java)
            startActivity(intent)
        }

        context = this
        swipeLayout.setOnRefreshListener{
            if(isNetwork(this) == 1)
            {
                Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show()
                swipeLayout.isRefreshing = false
                loadData()
            }
            else
            {
                val intent_1 = getIntent()
                finish()
                swipeLayout.isRefreshing = false
                startActivity(intent_1)
            }
        }

        val data = 0
        if(isNetwork(this) == 0)
        {
            Toast.makeText(this, "Pull down to refresh", Toast.LENGTH_SHORT).show()
            val snackbar: Snackbar = Snackbar.make(recyclerView, "Please ensure an active internet connection", Snackbar.LENGTH_SHORT)
            snackbar.show()
        }
        else
        {
           loadData()
            Log.d("data", "data()")
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }



    override fun RecyclerViewClickListenerMethod(imageData: ImageProperties, choice : Int)
    {
        val image: ImageEntity = ImageEntity(1, imageData.downLoadUrl, imageData.displayUrl, imageData.author)

            val intent: Intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("ImageUrl", imageData.displayUrl)
            intent.putExtra("ImageAuthor", imageData.author)
            intent.putExtra("DownloadUrl", imageData.downLoadUrl)
            startActivity(intent)
    }

    override fun RecyclerViewClickListenerEntity(images: FavouriteImages) {

    }

    private fun loadData()
    {
        imageViewModel = ViewModelProviders.of(this).get(ImageViewModel::class.java)
        imageViewModel.getImageProperties()
        imageViewModel.imageList.observe(this,
            Observer{ imagePropertiesList->
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.setHasFixedSize(true)
                recyclerView.adapter = ImageAdapter(imagePropertiesList, this, this)
            })
    }

    private fun isNetwork(context : Context): Int
    {
        val appContext = context.applicationContext
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo.also{

            if(it != null && it.isConnected == true)
            {
                return 1
            }
            else
            {
                return 0
            }

        }

    }

    override fun onStart() {
        super.onStart()
        loadData()
    }
}

//@Suppress("UNUSED_LAMBDA_EXPRESSION")

//        Log.d("mList", "${mList.size}")
//
//        textView = findViewById(R.id.data);
//        imageAdapter = ImageAdapter(mList)
//
//        val recyclerView1: RecyclerView = findViewById(R.id.recyclerView)
//        recyclerView1.setHasFixedSize(true)
//        recyclerView1.layoutManager = LinearLayoutManager(this)
//        recyclerView1.adapter = imageAdapter
//        textView.text = " mList ${mList.size} ||"
//        Log.d("dataList", " list ${mList.size} list")
//        for(images in mList)
//        {
//            Toast.makeText(this, "images", Toast.LENGTH_SHORT).show()
//            Log.d("data", "data ${images.displayUrl}")
//        }
//        Log.d("data", "images got")
////        recyclerView.adapter = ImageAdapter(mList, this)
////        recyclerView.setHasFixedSize(true)
////        recyclerView.layoutManager = LinearLayoutManager(this)
////        Toast.makeText(this, "Sucess", Toast.LENGTH_SHORT).show()
//    }
//
////    public fun getApiService(context: Context) : ArrayList<ImageProperties>
////    {
////        var image: ArrayList<ImageProperties> = ArrayList()
////        ImageApi.retrofitService.getProperties().enqueue( object: Callback<List<ImageProperties>>
////        {
////            override fun onResponse(
////                call: Call<List<ImageProperties>>,
////                response: Response<List<ImageProperties>>
////            ){
////                for(images in response.body()!!)
////                {
////                    Log.d("response", "inserted ${images.displayUrl}")
////                    mList.add(images)
////                }
////                for(images in mList)
////                {
////                    Log.d("response2", "response2 ${images.displayUrl}")
////                }
////                Log.d("response2", "response ${mList.size}")
////                Toast.makeText(context, "success ${mList.size}", Toast.LENGTH_SHORT).show()
////            }
////            override fun onFailure(call: Call<List<ImageProperties>>, t: Throwable) {
////                Toast.makeText( context, "Just success", Toast.LENGTH_SHORT).show()
////            }
////        })
////        return image
// //   }
//}
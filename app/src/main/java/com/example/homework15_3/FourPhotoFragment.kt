package com.example.homework15_3


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.homework15_3.databinding.FragmentFourPhotoBinding
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.util.concurrent.Executors


class FourPhotoFragment : Fragment(R.layout.fragment_four_photo) {
    private lateinit var binding: FragmentFourPhotoBinding
    val url1 = "https://www.thefamouspeople.com/profiles/images/ariana-grande-1.jpg"
    private val url2 = "https://picsum.photos/2000/2000"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        showImage()
        binding.button.setOnClickListener {
            replaceImages()
        }
    }

    private fun fetchImages(): List<Image>? {
        var randomImageList: List<Image>? = listOf()
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://picsum.photos/v2/list?limit=20")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.message?.let { Log.d("tag", it) }
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                randomImageList = Gson().fromJson(json, Array<Image>::class.java).toList()
                activity?.runOnUiThread {
                    Toast.makeText(context, "${randomImageList!![0]}", Toast.LENGTH_LONG).show()
                }
            }

        })
        return randomImageList
    }

    fun replaceImages() {

        val images = fetchImages()

        if (images != null) {
            Glide.with(this)
                .load(images[0].downloadUrl)
                .into(binding.photo1)

            Glide.with(this)
                .load(images[1].downloadUrl)
                .into(binding.photo2)

            Glide.with(this)
                .load(images[2].downloadUrl)
                .into(binding.photo3)

            Glide.with(this)
                .load(images[3].downloadUrl)
                .into(binding.photo4)
        }
    }

    private fun showImage() {
        val imageUrl1 = ImageUrl(url2)
        val imageUrl2 = ImageUrl(url2)
        val imageUrl3 = ImageUrl(url2)
        val imageUrl4 = ImageUrl(url2)
        binding.imageUrl1 = imageUrl1
        binding.imageUrl2 = imageUrl2
        binding.imageUrl3 = imageUrl3
        binding.imageUrl4 = imageUrl4

    }
}
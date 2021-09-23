package com.example.demo.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.demo.R
import com.example.demo.data.RestaurantData

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data = intent.getParcelableExtra<RestaurantData>(RESTAURANT_DATA)
        findViewById<TextView>(R.id.judul).apply {
            text = data?.name
        }

        findViewById<TextView>(R.id.deskripsi).apply {
            text = data?.description
        }

        findViewById<ImageView>(R.id.detail_gambar).apply {
            Glide.with(context).load(data?.pictureURL).into(this)
        }
    }

    companion object {
        const val RESTAURANT_DATA = "RESTAURANT_DATA"
    }
}
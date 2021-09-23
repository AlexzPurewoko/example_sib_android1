package com.example.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo.R
import com.example.demo.data.RestaurantData

class AdapterRecycler(
    private val onClickAction: (RestaurantData) -> Unit
) : RecyclerView.Adapter<RViewHolder>() {

    private val listData: MutableList<RestaurantData> = mutableListOf()

    fun addData(restaurantData: RestaurantData){
        listData.add(restaurantData)
        notifyItemInserted(listData.size - 1)
    }

    fun clearData() {
        val itemCounted = itemCount
        listData.clear()
        notifyItemRangeRemoved(0, itemCounted)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_data, parent, false)

        return RViewHolder(view)
    }

    override fun onBindViewHolder(holder: RViewHolder, position: Int) {
        holder.bind(listData[position])
        holder.itemView.setOnClickListener { onClickAction(listData[position]) }
    }

    override fun getItemCount() = listData.size


}

class RViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    fun bind(data: RestaurantData){
        itemView.findViewById<TextView>(R.id.judul).apply {
            text = data.name
        }

        itemView.findViewById<TextView>(R.id.deskripsi).apply {
            text = data.description
        }

        itemView.findViewById<ImageView>(R.id.gambar).apply {
            Glide.with(context).load(data.pictureURL).into(this)
        }
    }
}

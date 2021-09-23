package com.example.demo.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.adapter.AdapterRecycler
import com.example.demo.data.RestaurantData
import com.example.demo.ui.detail.DetailActivity
import com.example.demo.util.BtnClickType

class MainActivity : AppCompatActivity(), MainView<RestaurantData> {

    private lateinit var recyclerViewAdapter: AdapterRecycler

    private lateinit var mainLogic: MainLogic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainLogic = MainLogicImpl(this, this)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        recyclerViewAdapter = AdapterRecycler { restaurant ->
            mainLogic.clickBtn(BtnClickType.ITEM_CLICKED.also { it.data =  restaurant})
        }


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerViewAdapter

        findViewById<Button>(R.id.loadBtn).setOnClickListener {
            mainLogic.clickBtn(BtnClickType.LOAD_CLICKED)
        }

        findViewById<Button>(R.id.clearBtn).setOnClickListener {
            mainLogic.clickBtn(BtnClickType.CLEAR_CLICKED)
        }
    }

    override fun deliveredData(data: RestaurantData) {
        recyclerViewAdapter.addData(data)
    }

    override fun onBtnClick(type: BtnClickType) {
        when(type){
            BtnClickType.ITEM_CLICKED -> {
                Toast.makeText(this, "ITEM CLICKED!", Toast.LENGTH_SHORT)
                    .show()

                type.data?.let {
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.RESTAURANT_DATA, it as RestaurantData)
                    startActivity(intent)
                }

            }
            BtnClickType.CLEAR_CLICKED -> recyclerViewAdapter.clearData()
            BtnClickType.LOAD_CLICKED -> mainLogic.load()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainLogic.close()
    }
}
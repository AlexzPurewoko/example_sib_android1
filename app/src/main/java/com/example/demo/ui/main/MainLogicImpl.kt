package com.example.demo.ui.main

import android.content.Context
import com.example.demo.data.RestaurantData
import com.example.demo.loader.LoadData
import com.example.demo.loader.StreamLoadImpl
import com.example.demo.util.BtnClickType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainLogicImpl(
    private val ctx: Context,
    private val view: MainView<RestaurantData>
) : MainLogic {
    private val buttonClickBroadcaster: Channel<BtnClickType> = Channel {  }
    private val mainScope = MainScope()
    private val dataLoader: LoadData<RestaurantData> = StreamLoadImpl(ctx.resources)


    init {
        initChannel()
    }

    private fun initChannel() {
        mainScope.launch(Dispatchers.Default) {
            for (event in buttonClickBroadcaster){
                launch(Dispatchers.Main) {
                    view.onBtnClick(event)
                }
            }
        }
    }

    override fun load() {
        mainScope.launch(Dispatchers.Default) {
            dataLoader.stream().collect {
                launch(Dispatchers.Main) { view.deliveredData(it) }
            }
        }
    }

    override fun clickBtn(btnClickType: BtnClickType) {
        mainScope.launch {
            buttonClickBroadcaster.send(btnClickType)
        }
    }

    override fun close() {
        buttonClickBroadcaster.close()
    }
}
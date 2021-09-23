package com.example.demo.ui.main

import com.example.demo.util.BtnClickType

interface MainView<Data> {
    fun deliveredData(data: Data)
    fun onBtnClick(type: BtnClickType)
}


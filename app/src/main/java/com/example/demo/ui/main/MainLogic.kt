package com.example.demo.ui.main

import com.example.demo.util.BtnClickType
import java.io.Closeable

interface MainLogic: Closeable {
    fun load()
    fun clickBtn(btnClickType: BtnClickType)
}
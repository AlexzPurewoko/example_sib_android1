package com.example.demo.loader

import kotlinx.coroutines.flow.Flow
import java.io.Closeable

interface LoadData<ItemData> {
    fun stream(): Flow<ItemData>
    fun loadAll(): List<ItemData>
}
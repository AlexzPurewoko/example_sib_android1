package com.example.demo.loader

import android.content.res.Resources
import android.util.JsonReader
import com.example.demo.R
import com.example.demo.data.RestaurantData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import java.io.Closeable
import java.io.InputStream
import java.io.InputStreamReader

class StreamLoadImpl(
    private val resources: Resources
): LoadData<RestaurantData> {

    override fun stream(): Flow<RestaurantData> {
        val fileStream = resources.openRawResource(R.raw.restaurant)
        val jsonReader = JsonReader(InputStreamReader(fileStream))
        jsonReader.beginObject()

        // pass to property restaurants
        while(jsonReader.hasNext()){
            if(jsonReader.nextName() == "restaurants")
                break
            jsonReader.skipValue()
        }

        return flow {
            jsonReader.beginArray()
            while(jsonReader.hasNext()){
                delay(500)
                composeItem(jsonReader)?.let { emit(it) }
            }
            jsonReader.endArray()
        }.onCompletion {
            jsonReader.endObject()
            jsonReader.close()
        }.catch {
            jsonReader.close()
        }


    }

    private fun composeItem(jsonReader: JsonReader): RestaurantData? {
        return jsonReader.let {
            var name = ""
            var description = ""
            var pictureURL = ""
            it.beginObject()
            while(it.hasNext()){
                when(it.nextName()){
                    "name" -> name = it.nextString()
                    "description" -> description = it.nextString()
                    "pictureId" -> pictureURL = "https://restaurant-api.dicoding.dev/images/small/${it.nextString()}"
                    else -> it.skipValue()
                }
            }
            it.endObject()
            RestaurantData(name, description, pictureURL)
        }
    }

    override fun loadAll(): List<RestaurantData> {
        TODO("Not yet implemented")
    }
}
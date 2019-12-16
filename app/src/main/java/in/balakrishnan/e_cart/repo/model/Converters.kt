package `in`.balakrishnan.e_cart.repo.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    private val gson: Gson = GsonBuilder().create()
    private val type: Type = object : TypeToken<List<String?>?>() {}.type
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return value?.let {
            gson.fromJson(value, type)
        }
    }

    @TypeConverter
    fun fromList(value: List<String>?): String? {
        return gson.toJson(value, type)
    }
}
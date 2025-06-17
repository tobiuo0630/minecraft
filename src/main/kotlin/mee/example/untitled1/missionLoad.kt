package mee.example.untitled1

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object missionLoad {
    private val gson = Gson()

    fun loadFile(file: File): List<missionData> {
        if (!file.exists()) return emptyList()//.exists()はファイルがあるか確認する　emptyList()は要素が空のリスト
        val type = object : TypeToken<List<missionData>>() {}.type
        return file.reader().use {
            gson.fromJson(it, type)//useが拡張関数
        }
    }

    fun save(file: File,missions: List<missionData>){
        file.writer().use{gson.toJson(missions,it)}
    }
}
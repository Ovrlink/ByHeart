package com.example.byheart.utils

import android.content.Context
import com.example.byheart.R
import org.json.JSONObject
import kotlin.random.Random

object Utils {

    fun getRandomEnglishWordFromJson(context: Context): String {
        val inputStream = context.resources.openRawResource(R.raw.dictionary)
        val json = inputStream.bufferedReader().use { it.readText() }

        val jsonObject = JSONObject(json)
        val jsonArray = jsonObject.getJSONArray("words")
        val wordList = mutableListOf<String>()

        for (i in 0 until jsonArray.length()) {
            val wordObject = jsonArray.getJSONObject(i)
            val englishWord = wordObject.optString("englishWord")
            englishWord.let {
                wordList.add(it)
            }
        }

        return wordList.random(Random(System.currentTimeMillis()))
    }

}
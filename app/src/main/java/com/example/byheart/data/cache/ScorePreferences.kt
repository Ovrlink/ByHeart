package com.example.byheart.data.cache

import android.content.Context
import android.content.SharedPreferences

interface ScorePreferences { //to save data in cache of the app

    fun saveScore(context: Context, score: Int)

    fun getScore(context: Context): Int

    class Base() : ScorePreferences {

        companion object {
            private const val SCORE_KEY = "SCORE_KEY"
            private const val SCORE_PREFERENCES = "SCORE_PREFERENCES"
        }

        override fun saveScore(context: Context, score: Int) {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(SCORE_PREFERENCES, Context.MODE_PRIVATE) //initialize private instance and pass current score to this instance
            sharedPreferences.edit().putInt(SCORE_KEY, score).apply()
        }

        override fun getScore(context: Context): Int {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(SCORE_PREFERENCES, Context.MODE_PRIVATE) //initialize private instance and give it a value of 0
            return sharedPreferences.getInt(SCORE_KEY, 0)
        }

    }

}
package com.example.byheart.presentation.practice

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.byheart.data.cache.ScorePreferences

class PracticeViewModel : ViewModel() {

    private val scorePreferences = ScorePreferences.Base()

    fun getScore(context: Context): Int = scorePreferences.getScore(context)

}
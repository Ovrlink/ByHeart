package com.example.byheart.presentation.quiz

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.byheart.data.Repository
import com.example.byheart.data.RetrofitInstance
import com.example.byheart.data.cache.ScorePreferences
import com.example.byheart.data.core.ApiResult
import com.example.byheart.data.model.Word
import com.example.byheart.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private val repository = Repository.Base(RetrofitInstance.api)
    private val scorePreferences = ScorePreferences.Base()

    private val correctWordLiveData = MutableLiveData<Word>()
    private val errorLiveData = MutableLiveData<Boolean>()
    private val progressLiveData = MutableLiveData<Int>()

    fun generateQuiz(context: Context) {
        progressLiveData.postValue(View.VISIBLE)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getDefinition(Utils.getRandomEnglishWordFromJson(context))
            if (result is ApiResult.Success) {
                correctWordLiveData.postValue(result.word)
                errorLiveData.postValue(false)
            } else {
                errorLiveData.postValue(true)
            }
            progressLiveData.postValue(View.GONE)
        }
    }

    fun addOneScore(context: Context) {
        scorePreferences.saveScore(context, scorePreferences.getScore(context) + 1)
    }

    fun correctWordLiveData(): LiveData<Word> = correctWordLiveData
    fun errorLiveData(): LiveData<Boolean> = errorLiveData
    fun progressLiveData(): LiveData<Int> = progressLiveData

}
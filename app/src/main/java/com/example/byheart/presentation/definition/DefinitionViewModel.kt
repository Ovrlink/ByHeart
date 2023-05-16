package com.example.byheart.presentation.definition

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.byheart.R
import com.example.byheart.data.Repository
import com.example.byheart.data.RetrofitInstance
import com.example.byheart.data.core.ApiResult
import com.example.byheart.data.model.Word
import com.example.byheart.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class DefinitionViewModel : ViewModel() {

    private val repository = Repository.Base(RetrofitInstance.api)

    private val wordLiveData = MutableLiveData<Word>()
    private val progressLiveData = MutableLiveData<Int>()
    private val errorLiveData = MutableLiveData<Boolean>()

    fun getDefinition(word: String) {
        progressLiveData.postValue(View.VISIBLE)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getDefinition(word)
            if (result is ApiResult.Error) {
                errorLiveData.postValue(true)
            } else {
                errorLiveData.postValue(false)
                wordLiveData.postValue(result.word)
            }
            progressLiveData.postValue(View.GONE)
        }
    }

    fun defineRandomWord(context: Context) {
        val randomWord = Utils.getRandomEnglishWordFromJson(context)
        getDefinition(randomWord)
    }

    fun wordLiveData(): LiveData<Word> = wordLiveData
    fun progressLiveData(): LiveData<Int> = progressLiveData
    fun errorLiveData(): LiveData<Boolean> = errorLiveData

}
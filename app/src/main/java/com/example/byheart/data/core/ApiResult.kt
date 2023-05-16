package com.example.byheart.data.core

import com.example.byheart.data.model.License
import com.example.byheart.data.model.Word

sealed class ApiResult(val word: Word) {

    class Success(word: Word) : ApiResult(word = word)

    object Error : ApiResult(word = Word(License("", ""), emptyList(), emptyList(), emptyList(), ""))

}
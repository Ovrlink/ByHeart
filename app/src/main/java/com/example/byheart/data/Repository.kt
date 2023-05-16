package com.example.byheart.data

import com.example.byheart.data.core.ApiResult

interface Repository {

    suspend fun getDefinition(word: String): ApiResult

    class Base(private val api: DictionaryApi) : Repository {
        override suspend fun getDefinition(word: String): ApiResult {
            val result = api.getDefinition(word)

            return if (result.isSuccessful) {
                val resultWord = result.body()
                if (resultWord != null) {
                    ApiResult.Success(resultWord.first())
                } else {
                    ApiResult.Error
                }
            } else {
                ApiResult.Error
            }
        }
    }

}
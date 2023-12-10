package me.fitroh.londriforowner.di

import android.content.Context
import me.fitroh.londriforowner.data.api.ApiConfig
import me.fitroh.londriforowner.pref.UserPreference
import me.fitroh.londriforowner.pref.dataStore
import me.fitroh.londriforowner.utils.UserRepository

object Injection {
    fun provideRepository (context : Context):UserRepository{
        val apiService = ApiConfig.getApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref, apiService)
    }
}
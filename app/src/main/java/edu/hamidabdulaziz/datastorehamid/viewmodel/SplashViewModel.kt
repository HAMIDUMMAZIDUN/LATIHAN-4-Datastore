package edu.hamidabdulaziz.datastorehamid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import edu.hamidabdulaziz.datastorehamid.data.UserPreferencesRepository
import edu.hamidabdulaziz.datastorehamid.model.User
import kotlinx.coroutines.flow.Flow

class SplashViewModel(private val userPreferencesRepository: UserPreferencesRepository) : ViewModel() {
    val userSession: Flow<User?> = userPreferencesRepository.userFlow

    // Or if you prefer LiveData in Activity
    // val userSessionLiveData = userPreferencesRepository.userFlow.asLiveData()
}

class SplashViewModelFactory(private val repository: UserPreferencesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
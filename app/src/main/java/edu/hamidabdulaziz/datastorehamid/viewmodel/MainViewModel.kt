package edu.hamidabdulaziz.datastorehamid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import edu.hamidabdulaziz.datastorehamid.data.UserPreferencesRepository
import edu.hamidabdulaziz.datastorehamid.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val userPreferencesRepository: UserPreferencesRepository) : ViewModel() {
    val userDetails: Flow<User?> = userPreferencesRepository.userFlow

    // Or if you prefer LiveData
    // val userDetailsLiveData = userPreferencesRepository.userFlow.asLiveData()

    fun logoutUser() {
        viewModelScope.launch {
            userPreferencesRepository.clearUser()
        }
    }
}

class MainViewModelFactory(private val repository: UserPreferencesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
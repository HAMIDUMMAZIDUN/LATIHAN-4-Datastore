package edu.hamidabdulaziz.datastorehamid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.hamidabdulaziz.datastorehamid.data.UserPreferencesRepository
import edu.hamidabdulaziz.datastorehamid.model.User
import kotlinx.coroutines.launch

class LoginViewModel(private val userPreferencesRepository: UserPreferencesRepository) : ViewModel() {

    fun loginUser(name: String, email: String, phoneNumber: String) {
        viewModelScope.launch {
            val user = User(name, email, phoneNumber)
            userPreferencesRepository.saveUser(user)
        }
    }
}

class LoginViewModelFactory(private val repository: UserPreferencesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
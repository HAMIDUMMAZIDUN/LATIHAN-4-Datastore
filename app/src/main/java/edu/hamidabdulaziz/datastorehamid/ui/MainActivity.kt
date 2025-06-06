package edu.hamidabdulaziz.datastorehamid.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import edu.hamidabdulaziz.datastorehamid.R
import edu.hamidabdulaziz.datastorehamid.data.UserPreferencesRepository
import edu.hamidabdulaziz.datastorehamid.data.dataStore
import edu.hamidabdulaziz.datastorehamid.viewmodel.MainViewModel
import edu.hamidabdulaziz.datastorehamid.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var textViewName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewPhone: TextView
    private lateinit var buttonLogout: Button

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(UserPreferencesRepository.getInstance(this.dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewName = findViewById(R.id.textViewName)
        textViewEmail = findViewById(R.id.textViewEmail)
        textViewPhone = findViewById(R.id.textViewPhone)
        buttonLogout = findViewById(R.id.buttonLogout)

        lifecycleScope.launch {
            mainViewModel.userDetails.collect { user ->
                if (user != null) {
                    textViewName.text = "Nama Anda: ${user.name}"
                    textViewEmail.text = "Email Anda: ${user.email}"
                    textViewPhone.text = "No HP Anda: ${user.phoneNumber}"
                } else {
                    // Should not happen if session validation is correct, but as a fallback:
                    Toast.makeText(this@MainActivity, "User data not found, logging out.", Toast.LENGTH_LONG).show()
                    navigateToLogin()
                }
            }
        }

        buttonLogout.setOnClickListener {
            mainViewModel.logoutUser()
            Toast.makeText(this, "Logout Berhasil!", Toast.LENGTH_SHORT).show()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
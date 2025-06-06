package edu.hamidabdulaziz.datastorehamid.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import edu.hamidabdulaziz.datastorehamid.R
import edu.hamidabdulaziz.datastorehamid.data.UserPreferencesRepository
import edu.hamidabdulaziz.datastorehamid.data.dataStore
import edu.hamidabdulaziz.datastorehamid.viewmodel.SplashViewModel
import edu.hamidabdulaziz.datastorehamid.viewmodel.SplashViewModelFactory
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen") // Suppress if you are not using the official Splash Screen API
class SplashScreenActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels {
        SplashViewModelFactory(UserPreferencesRepository.getInstance(this.dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Observe user session
        lifecycleScope.launch {
            splashViewModel.userSession.collect { user ->
                // Using a small delay for splash screen visibility,
                // otherwise it might be too fast if data is already cached.
                Handler(Looper.getMainLooper()).postDelayed({
                    if (user != null) {
                        // User is logged in
                        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    } else {
                        // User is not logged in
                        startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                    }
                    finish() // Finish SplashScreenActivity
                }, 1500) // 1.5 seconds delay
            }
        }
    }
}
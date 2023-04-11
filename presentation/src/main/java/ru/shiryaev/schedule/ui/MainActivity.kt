package ru.shiryaev.schedule.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Устанавливаем тему
        setTheme()

        binding = ActivityMainBinding.inflate(layoutInflater)

        // Реклама - начало
        MobileAds.initialize(this)
        binding.adView.loadAd(AdRequest.Builder().build())
        // Реклама - конец

        setContentView(binding.root)

        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = host.navController
        binding.homeScreenBnv.setupWithNavController(navController)
    }

    private fun setTheme() {
        val listThemeMode = resources.getStringArray(R.array.theme_mode_entries)
        when (PreferenceManager.getDefaultSharedPreferences(this).getString(resources.getString(R.string.theme_key), listThemeMode.first())) {
            listThemeMode.first() -> { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) }
            listThemeMode[1] -> { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) }
            listThemeMode.last() -> { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }
        }
    }
}
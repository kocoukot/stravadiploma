package com.example.stravadiploma

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.stravadiploma.auth.AuthFragment
import com.example.stravadiploma.databinding.ActivityMainBinding
import com.example.stravadiploma.useractivitylist.UserActivityFragment
import com.example.stravadiploma.ui.OnboardingFragment
import com.example.stravadiploma.profile.ProfileFragment
import com.example.stravadiploma.utils.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val sharedPref by lazy {
        getSharedPreferences(
            Constants.SHARED_PREF,
            Context.MODE_PRIVATE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fragment = getFirstFragment()
            supportFragmentManager.beginTransaction().add(R.id.frame_for_Fragment, fragment)
                .commit()
        }
    }

    private fun getFirstFragment(): Fragment {
        return when (sharedPref.getBoolean(Constants.IS_FIRST_TIME_LAUNCH_KEY, true)) {
            true -> OnboardingFragment()
            else -> AuthFragment()
        }
    }
}
package com.example.stravadiploma

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationSet
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.fragment.app.Fragment
import com.example.stravadiploma.auth.AuthFragment
import com.example.stravadiploma.onBoarding.OnboardingFragment
import com.example.stravadiploma.utils.Constants

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
            val transaction = supportFragmentManager.beginTransaction().add(R.id.frame_for_Fragment, fragment)
            transaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out)
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
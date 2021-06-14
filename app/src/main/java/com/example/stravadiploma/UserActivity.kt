package com.example.stravadiploma

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.stravadiploma.databinding.ActivityMainBinding
import com.example.stravadiploma.databinding.ActivityUserBinding
import com.example.stravadiploma.profile.ProfileFragment
import com.example.stravadiploma.useractivitylist.UserActivityFragment
import com.example.stravadiploma.utils.AutoClearedValue
import com.example.stravadiploma.utils.LogInfo
import com.example.stravadiploma.utils.autoCleared
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserActivity : AppCompatActivity() {

    private val fragmentProfile = ProfileFragment()
    private val fragmentActivity = UserActivityFragment()
    private val binding by viewBinding(ActivityUserBinding::bind)


    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

            when (menuItem.itemId) {
                R.id.profile -> {
                    transaction
                        .replace(R.id.frameForUser, fragmentProfile)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.activities -> {
                    transaction
                        .replace(R.id.frameForUser, fragmentActivity)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.frameForUser, fragmentProfile)
                .commit()
        }

        bindingViews()
    }

    private fun bindingViews() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(
            mOnNavigationItemSelectedListener
        )
    }

    fun setTitle(title: String) {
        binding.appTitle.text =  title
    }

    fun setBottomBarVisibility(isVisible: Boolean){
        binding.bottomNavigation.isVisible = isVisible

    }

}
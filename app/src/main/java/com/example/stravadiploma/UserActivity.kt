package com.example.stravadiploma

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.stravadiploma.databinding.ActivityUserBinding
import com.example.stravadiploma.profile.ProfileFragment
import com.example.stravadiploma.useractivitylist.UserActivityFragment

import com.google.android.material.bottomnavigation.BottomNavigationView

class UserActivity : AppCompatActivity() {

    private val fragmentProfile = ProfileFragment()
    private val fragmentActivity = UserActivityFragment()

    private val binding by viewBinding(ActivityUserBinding::bind)
    private var selectedFragment = R.id.profile

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            val transaction = supportFragmentManager.beginTransaction()

            if (selectedFragment != menuItem.itemId) {
                when (menuItem.itemId) {
                    R.id.profile -> {
                        transaction
                            .replace(R.id.fragmentStrava, fragmentProfile)
                            .commit()
                        selectedFragment = menuItem.itemId
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.activities -> {
                        transaction
                            .replace(R.id.fragmentStrava, fragmentActivity)
                            .commit()
                        selectedFragment = menuItem.itemId
                        return@OnNavigationItemSelectedListener true
                    }
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragmentStrava, fragmentProfile)
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
        binding.appTitle.text = title
    }

    fun setBottomBarVisibility(isVisible: Boolean) {
        binding.bottomNavigation.isVisible = isVisible

    }

}
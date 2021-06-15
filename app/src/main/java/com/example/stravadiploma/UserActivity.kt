package com.example.stravadiploma

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.stravadiploma.databinding.ActivityUserBinding
import com.example.stravadiploma.profile.ProfileFragmentDirections
import com.example.stravadiploma.useractivitylist.UserActivityFragmentDirections

import com.google.android.material.bottomnavigation.BottomNavigationView

class UserActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityUserBinding::bind)
    private var selectedFragment = R.id.profile

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            if (selectedFragment != menuItem.itemId) {
                when (menuItem.itemId) {
                    R.id.profile -> {
                        val action =
                            UserActivityFragmentDirections.actionUserActivityFragmentToProfileFragment2()
                        findNavController(R.id.fragmentStrava)

                            .navigate(action)
                        selectedFragment = menuItem.itemId
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.activities -> {
                        val action =
                            ProfileFragmentDirections.actionProfileFragmentToUserActivityFragment2()
                        findNavController(R.id.fragmentStrava).navigate(action)
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
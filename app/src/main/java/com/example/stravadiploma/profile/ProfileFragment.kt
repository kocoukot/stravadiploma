package com.example.stravadiploma.profile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.stravadiploma.MainActivity
import com.example.stravadiploma.R
import com.example.stravadiploma.UserActivity
import com.example.stravadiploma.data.UserProfile
import com.example.stravadiploma.databinding.FragmentProfileBinding
import com.example.stravadiploma.utils.logInfo
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    private var adapter: ArrayAdapter<String>? = null
    private var weightList = listOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUserProfile()
        weightList = viewModel.getWeightList()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as UserActivity).setTitle("Profile")
        bindViewModel()
        bindButtons()
        setWeightList("")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    private fun bindViewModel() {

        viewModel.userProfile.observe(viewLifecycleOwner) { setUserInfo(it) }
        viewModel.isLoading.observe(viewLifecycleOwner, ::infoLoading)
        viewModel.isProfileLogout.observe(viewLifecycleOwner) {logoutProfile()}

    }

    private fun bindButtons() {
        binding.logoutButton.setOnClickListener {
            makeAlert()
        }

        binding.shareProfileButton.setOnClickListener {
            Handler(Looper.getMainLooper()).post {
                constructPermissionsRequest(
                    android.Manifest.permission.READ_CONTACTS,
                    onShowRationale = ::onContactPermissionShowRationale,
                    onPermissionDenied = ::onContactPermissionDenied,
                    onNeverAskAgain = ::onContactPermissionNeverAsk,
                    requiresPermission = ::openContactsList
                ).launch()
            }
        }

        binding.weightTextView.setOnItemClickListener { _, _, position, _ ->
            viewModel.setUserNewWeight(weightList[position].toDouble())
        }
    }

    private fun makeAlert(){
        MaterialAlertDialogBuilder(requireContext(),
            R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
            .setMessage(resources.getString(R.string.logoutMessage))
            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
               dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                viewModel.logoutProfile()
            }
            .show()
    }

    private fun logoutProfile(){
        val activityClass = MainActivity::class.java
        val intent = Intent(requireContext(), activityClass)

        startActivity(intent)
        (requireContext() as UserActivity).finish()

    }

    private fun setUserInfo(user: UserProfile) {
        "${user.lastname} ${user.firstname}".also { binding.userName.text = it }
        "@${user.username}".also { binding.userNickname.text = it }
        binding.followersAmount.text = user.followerCount.toString()
        binding.followingAmount.text = user.friendCount.toString()
        binding.genderTextView.text = user.sex.name
        binding.countryTextView.text = user.country ?: "-"
        setWeightList(user.weight.toString())
        loadAvatar(user.avatar)
    }

    private fun loadAvatar(avatar: String) {
        Glide.with(binding.userAvatar)
            .load(avatar)
            .error(R.drawable.onb_first)
            .placeholder(R.drawable.onb_first)
            .into(binding.userAvatar)
    }

    private fun setWeightList(weightToSet: String) {
        val weight = if (weightToSet.isEmpty()) "50" else weightToSet
        adapter =
            ArrayAdapter(requireContext(), R.layout.list_item, weightList.map { w -> "$w kg" })
        binding.weightTextView.setText("$weight kg")
        binding.weightTextView.setAdapter(adapter)
    }

    private fun infoLoading(isLoading: Boolean){
        binding.loadingProfileProgressBar.isVisible = isLoading
        binding.logoutButton.isEnabled = !isLoading
        binding.shareProfileButton.isEnabled = !isLoading
        binding.weightTextView.isEnabled = !isLoading
    }

    private fun onContactPermissionDenied() {
        Toast.makeText(requireContext(), "Denied", Toast.LENGTH_SHORT).show()
    }

    private fun onContactPermissionShowRationale(request: PermissionRequest) {
        Toast.makeText(requireContext(), "Work", Toast.LENGTH_SHORT).show()
        request.proceed()
    }

    private fun onContactPermissionNeverAsk() {
        Toast.makeText(requireContext(), "Never ask", Toast.LENGTH_SHORT).show()
    }

    private fun openContactsList(){
        val action = viewModel.userProfile.value?.let {
            ProfileFragmentDirections.actionProfileFragmentToContactsFragment(
                it.id)
        }
        findNavController().navigate(action!!)
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as UserActivity).setBottomBarVisibility(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter = null
        _binding = null
    }

    companion object {
        const val USER_PROFILE_FRAGMENT_KEY = "userProfileFragmentKey"
    }
}
package com.example.stravadiploma.contacts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stravadiploma.R
import com.example.stravadiploma.UserActivity
import com.example.stravadiploma.adapters.contacts.ContactAdapter
import com.example.stravadiploma.databinding.FragmentContactsListBinding

class ContactsFragment: Fragment(R.layout.fragment_contacts_list) {

    private var _binding: FragmentContactsListBinding? = null
    private val binding get() = _binding!!

    private var contactAdapter: ContactAdapter? = null
    private val viewModel: ContactsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactsListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as UserActivity).setTitle("Contacts")
        (requireActivity() as UserActivity).setBottomBarVisibility(false)
        initList()
        bindViewModels()

    }

    private fun initList() {
        contactAdapter = ContactAdapter { id ->
            viewModel.shareUserProfile(id, userId)
        }
        with(binding.contactListRecycler) {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun bindViewModels() {
        viewModel.contactList.observe(viewLifecycleOwner) {
            contactAdapter?.items = it
        }
        viewModel.shareIntent.observe(viewLifecycleOwner, ::shareUserProfile)
        viewModel.getContacts()
    }

    private fun shareUserProfile(intent: Intent) {
        startActivity(intent)
    }


    companion object {
        private var userId: Long = 0

        fun newInstance(userId: Long): ContactsFragment {
            this.userId = userId
            return ContactsFragment()
        }
    }
}
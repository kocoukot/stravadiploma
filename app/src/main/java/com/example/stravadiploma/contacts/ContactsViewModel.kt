package com.example.stravadiploma.contacts

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.example.stravadiploma.data.Contact
import com.example.stravadiploma.utils.SingleLiveEvent
import com.example.stravadiploma.utils.logInfo
import kotlinx.coroutines.launch

class ContactsViewModel(application: Application) : AndroidViewModel(application) {

    private val contactRepository = ContactsRepository(application)

    private val _contactsList = MutableLiveData<List<Contact>>()
    private val _shareIntent = SingleLiveEvent<Intent>()
    val contactList: LiveData<List<Contact>>
        get() = _contactsList

    val shareIntent: LiveData<Intent>
        get() = _shareIntent

    fun getContacts() {
        viewModelScope.launch {
            try {
                _contactsList.postValue(contactRepository.getContacts())
            } catch (t: Throwable) {
                logInfo(t.localizedMessage)
                _contactsList.postValue(emptyList())
            }
        }
    }

    fun shareUserProfile(id: Int, userId: Long) {
        val contactNumber = contactList.value?.get(id)?.number ?: 0
        val uri = Uri.parse("smsto:$contactNumber")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra(

            Intent.EXTRA_TEXT, "Hi! I am using Strava. " +
                    "https://www.strava.com/athletes/$userId"
        )
        _shareIntent.postValue(intent)
    }
}
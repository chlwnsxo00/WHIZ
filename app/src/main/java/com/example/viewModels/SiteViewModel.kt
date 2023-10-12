package com.example.viewModels

import androidx.lifecycle.*
import com.example.db.SiteRepository
import com.example.room.Site
import kotlinx.coroutines.launch

class SiteViewModel(private val repository: SiteRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allSites: LiveData<List<Site>> = repository.allSites.asLiveData()
    val siteSize: LiveData<Int> = repository.size.asLiveData()
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: Site) = viewModelScope.launch {
        repository.insert(word)
    }
    fun delete(word: Site) = viewModelScope.launch {
        repository.delete(word)
    }
}

class SiteViewModelFactory(private val repository: SiteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SiteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SiteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
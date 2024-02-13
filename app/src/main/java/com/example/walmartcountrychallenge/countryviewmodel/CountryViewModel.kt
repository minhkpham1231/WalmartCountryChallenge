package com.example.walmartcountrychallenge.countryviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walmartcountrychallenge.network.CountryRepository
import com.example.walmartcountrychallenge.network.CountryRepositoryImpl
import com.example.walmartcountrychallenge.util.State

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountryViewModel(private val repository: CountryRepository =  CountryRepositoryImpl()): ViewModel() {
    private val _country: MutableLiveData<State> = MutableLiveData()
    val countryLiveData: LiveData<State> get() = _country

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getCountry() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCountry().collect {
                when (it) {
                    is State.LOADING -> {
                        _isLoading.postValue(it.loading)
                        _country.postValue(it)
                    }
                    is State.SUCCESS<*> -> {
                        _isLoading.postValue(it.isLoading)
                        _country.postValue(it)
                    }
                    is State.ERROR -> {
                        _country.postValue(it)
                        _isLoading.postValue(it.isLoading)
                    }
                }
            }
        }
    }
}
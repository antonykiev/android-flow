package com.example.flow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val helloWorld = "Hello world"

    private val _liveData = MutableLiveData(helloWorld)
    val liveData: LiveData<String> = _liveData

    private val _stateFlow = MutableStateFlow(helloWorld)
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    private val _channel = Channel<String>()
    val channel = _channel.receiveAsFlow()

    fun triggerLiveData() {
        _liveData.postValue("LiveData")
    }

    fun triggerStateFlow() {
        viewModelScope.launch {
            _stateFlow.emit("StateFlow")
        }
    }

    fun triggerSharedFlow() {
        viewModelScope.launch {
            _sharedFlow.emit("SharedFlow")
        }
    }

    fun triggerFlow(): Flow<Int> = flow {
        repeat(100) {
            emit(it)
            delay(1000L)
        }
    }

    fun triggerChanel() {
        viewModelScope.launch {
            _channel.send("Channel")
        }
    }

}
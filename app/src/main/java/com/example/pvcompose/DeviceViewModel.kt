package com.example.pvcompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class DeviceViewModel : ViewModel(
    
) {
    private val _devices: MutableLiveData<List<Device>> = MutableLiveData()
    val devices: LiveData<List<Device>> get() = _devices

    fun setDevices(newDevices: List<Device>) {
        _devices.value = newDevices
    }

    fun isEmpty(): Boolean {
        return _devices.value.isNullOrEmpty()
    }

    fun clear(){
        _devices.value = emptyList()
    }

    fun updateDevice(index: Int, newDevice: Device) {
        val currentDevices = _devices.value.orEmpty().toMutableList()
        if (index in currentDevices.indices) {
            currentDevices[index] = newDevice
            _devices.value = currentDevices
        }
    }

    companion object {
    }
}
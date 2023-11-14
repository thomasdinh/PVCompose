package com.example.pvcompose

data class Device(
    val id: Int, // Unique identifier for the device
    var hostname: String,
    var model: String,
    var brand: String,
    var deviceType: String

){
    fun get_id(): Int {
        return id
    }
}
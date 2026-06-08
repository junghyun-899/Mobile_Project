package com.example.mobile_project.model

data class TravelRecord(
    val id:Int = 0,
    var place:String,
    var date:String,
    var memo:String,
    var imagePath:String,
    var latitude:Double = 0.0,
    var longitude:Double = 0.0
)
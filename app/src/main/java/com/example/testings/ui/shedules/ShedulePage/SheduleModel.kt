package com.example.testings.ui.shedules.ShedulePage

class SheduleModel(
    var id: Int,
    var cab: Int,
    var title: String,
    var teacher: String,
    var start: String,
    var end: String,
    var weeks: String
) {
    var cours: Int = 0
    var faculty: String? = null
    var profile: String? = null
}
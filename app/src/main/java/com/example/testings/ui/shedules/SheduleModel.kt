package com.example.testings.ui.shedules

data class Less(val Start: String, val End:String)

enum class LessonNum(val value: Less){
    // дневная смена
    firFirst(Less("8:00", "9:35")),
    firSecond(Less("9:45", "11:20")),
    firThird(Less("11:30","13:05")),

    // вечерняя смена
    secFirst(Less("13:30", "15:05")),
    secSecond(Less("15:15", "16:50")),
    secThird(Less("17:00", "18:35")),
    secFour(Less("18:45", "20:20"))

}

class SheduleModel(
    var Title:String,
    var Cab:Int,
    var Teacher: String,
    var DateTimeStart: LessonNum
)

class GroupModel(
    val Name:String,
    val Cours:Int,
    val Faculty:String
)

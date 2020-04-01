package com.example.testings.ui.unstudents

class EducProfileModel(val Code: String?, val Name: String?, val Faculty: String?, val All: String?){

    var SpecialQute: EducType? = null
    var GeneralTerms: EducType? = null
    var Commercial: EducType? = null

    var Lessons: ArrayList<NeededLesson> = ArrayList(3)
}

class EducType(intrum: String, absent: String, int_abs: String){
    var Intramural: String = intrum
    var Absentia: String = absent
    var Intra_Absentia: String = int_abs
}

class NeededLesson(
    var Name: String,
    var Score: String
)


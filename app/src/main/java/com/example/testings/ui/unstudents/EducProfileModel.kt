package com.example.testings.ui.unstudents

class EducProfileModel(
    var Code: String?,
    var Name: String?,
    var Faculty: String?)
{
    var SpecialQute: EducType? = null
    var GeneralTerms: EducType? = null
    var Commercial: EducType? = null
    var Lessons: List<String>? = null
}

class EducType(
    var Intramural: String,
    var Absentia: String,
    var Intra_Absentia: String) {

    fun getAll(): Int {
        return Intramural.toInt() + Absentia.toInt() + Intra_Absentia.toInt()
    }
}

class ProfileItemModel(var title: String, var link: String)
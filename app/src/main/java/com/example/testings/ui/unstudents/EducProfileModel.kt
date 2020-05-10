package com.example.testings.ui.unstudents

class EducProfileModel(
    var Code: String?,
    var Name: String?,
    var Faculty: String?)
{
    var SpecialQute: EducType? = null
    var GeneralTerms: EducType? = null
    var Commercial: EducType? = null
    var Lessons: ArrayList<String>? = null
    var Scores: String? = null
}

class EducType(
    var Intramural: String,
    var Absentia: String,
    var Intra_Absentia: String)

class ProfileItemModel(var title: String, var link: String)
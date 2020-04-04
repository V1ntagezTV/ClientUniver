package com.example.testings.ui.unstudents

class EducProfileModel(
    val Code: String?,
    val Name: String?,
    val Faculty: String?,
    val All: String?
) {
    var SpecialQute: EducType? = null
    var GeneralTerms: EducType? = null
    var Commercial: EducType? = null
    var Lessons: String? = null
    var Scores: String? = null
}

class EducType(
    val Intramural: String,
    val Absentia: String,
    val Intra_Absentia: String)


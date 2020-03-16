package com.example.testings.ui.unstudents

class EducProfileModel(code: String, name: String, faculty: String, all: String){
    var Code: String
    var Name: String
    var Faculty: String
    var All: String

    var SpecialQute: EducTypes? = null
    var GeneralTerms: EducTypes? = null
    var Commercial: EducTypes? = null

    init {
        this.Code = code
        this.Name = name
        this.Faculty = faculty
        this.All = all
    }
}

class EducTypes(name:String, intrum: Int, absent: Int, int_abs: Int){
        var Name: String = name
        var Intramural: Int = intrum
        var Absentia: Int = absent
        var Intra_Absentia: Int = int_abs
}
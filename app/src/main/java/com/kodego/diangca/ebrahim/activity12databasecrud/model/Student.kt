package com.kodego.diangca.ebrahim.activity12databasecrud.model

import com.kodego.diangca.ebrahim.activity12databasecrud.R

class Student (var firstName:String = "Unknown", var lastName:String = "Unknown", var img:Int){

    var id: Int = 0
    var yearstarted : Int = 0
    var course = ""
    constructor(): this("", "", R.drawable.placeholder){}
}

class StudentContacts(){
      var student:Student = Student()
      var contacts = ArrayList<Contact>()

}
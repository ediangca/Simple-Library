package com.kodego.diangca.ebrahim.activity12databasecrud.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Contact
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Student
import com.kodego.diangca.ebrahim.activity12databasecrud.model.StudentContacts

interface StudentDAO {
         fun addStudent(student: Student)
         fun getStudents() : ArrayList<Student>
         fun updateStudent(studentId: Int, student: Student)
         fun deleteStudent(studentId:Int)
         fun searchStudentByLastName(searchString: String) : ArrayList<Student>  //new code added Jan 26,2023

         fun getStudentsWithContacts() : ArrayList<StudentContacts>
         fun getStudentsByLastNameAndFirstName(firstName:String, lastName: String) : ArrayList<Student>
}

interface ContactDAO{

    fun addContact(contact: Contact)
    fun addContacts(contacts: ArrayList<Contact>)
    fun getContacts(studentID: Int): ArrayList<Contact>
    fun updateContact(contact: Contact)
    fun deleteContact(studentID: Int)
}

class StudentDAOSQLImpl(var context: Context): StudentDAO{

    override fun addStudent(student: Student) {
        var databaseHandler:DatabaseHandler = DatabaseHandler(context) //declared to access database
        var db = databaseHandler.writableDatabase //actual access in database

        val contentValues = ContentValues() //to put on database table
        contentValues.put(DatabaseHandler.studentFirstName, student.firstName)
        contentValues.put(DatabaseHandler.studentlastName, student.lastName)
// to call database
        val success = db.insert(DatabaseHandler.tableStudents, null, contentValues) // actual insert to database
        db.close()
    }//ensure to close

    override fun getStudents(): ArrayList<Student> {
        val studentList: ArrayList<Student> = ArrayList()  //declared the data to be returned

        val selectQuery = "SELECT ${DatabaseHandler.studentlastName}, " +
                 "${DatabaseHandler.studentFirstName}, " +
                 "${DatabaseHandler.studentId}, " +
                 "${DatabaseHandler.yearstarted}, " +
                 "${DatabaseHandler.course} " +
                 "FROM ${DatabaseHandler.tableStudents}"    

        var databaseHandler:DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)  //to retrieve data
        }catch (e: SQLiteException) {
            db.close()
            return ArrayList()
        }

        var student = Student()
        if (cursor.moveToFirst()){
            do {
                student = Student()
                student.id = cursor.getInt(2)
                student.lastName = cursor.getString(0)
                student.firstName = cursor.getString(1)
                student.yearstarted = cursor.getInt(3)
                studentList.add(student)
            }while(cursor.moveToNext())
    }
    db.close()
    return studentList
}


   @SuppressLint("SuspiciousIndentation")
   override fun updateStudent(studentId: Int, student: Student){
       var databaseHandler:DatabaseHandler = DatabaseHandler(context)
       var db = databaseHandler.writableDatabase

       val contentValues = ContentValues()
       contentValues.put(DatabaseHandler.studentFirstName, student.firstName)
       contentValues.put(DatabaseHandler.studentlastName, student.lastName)

       val values = arrayOf("$studentId")

       val success = db.update(DatabaseHandler.tableStudents,
           contentValues,
           "${DatabaseHandler.studentId} = ?",
           values)
           db.close()

   }

    override fun deleteStudent(studentId: Int){
            var databaseHandler: DatabaseHandler = DatabaseHandler(context)
            val db = databaseHandler.writableDatabase

            val values = arrayOf("$studentId")
            val success = db.delete(
                DatabaseHandler.tableStudents,
                "${DatabaseHandler.studentId} = ?",
                values
            )
            db.close()
        }



    //new code added Jan 26,2023
    override fun searchStudentByLastName(searchString: String) : ArrayList<Student> {
        val studentList: ArrayList<Student> = ArrayList()

        val columns = arrayOf(DatabaseHandler.studentFirstName,
                DatabaseHandler.studentlastName,
                DatabaseHandler.studentId,
                DatabaseHandler.yearstarted,
                DatabaseHandler.course)

        var databaseHandler:DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.query(DatabaseHandler.tableStudents,
            columns,
            "${DatabaseHandler.studentlastName} like '%${searchString}%'",
            null,
            null,
            null,
            DatabaseHandler.studentlastName
            )
        }catch (e: SQLiteException) {
            db.close()
            return ArrayList()
        }

        var student = Student()
        if (cursor.moveToFirst()){
            do {
                student = Student()
                student.id = cursor.getInt(2)
                student.lastName = cursor.getString(0)
                student.firstName = cursor.getString(1)
                student.yearstarted = cursor.getInt(3)
                studentList.add(student)
            }while(cursor.moveToNext())
        }
        db.close()
        return studentList
    }

    override fun getStudentsWithContacts(): ArrayList<StudentContacts> {
        val studentWithContactsList: ArrayList<StudentContacts> = ArrayList()  //declared the data to be returned

        val selectQuery = "SELECT ${DatabaseHandler.studentlastName}, " +
                "${DatabaseHandler.studentFirstName}, " +
                "${DatabaseHandler.studentId}, " +
                "${DatabaseHandler.yearstarted}, " +
                "${DatabaseHandler.course} " +
                "FROM ${DatabaseHandler.tableStudents}"

        var databaseHandler:DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)  //to retrieve data
        }catch (e: SQLiteException) {
            db.close()
            return ArrayList()
        }

        var studentWithContact = StudentContacts()
        if (cursor.moveToFirst()){
            do {
                var student = Student()
                studentWithContact = StudentContacts()
                student.id = cursor.getInt(2)
                student.lastName = cursor.getString(0)
                student.firstName = cursor.getString(1)
                student.yearstarted = cursor.getInt(3)
                studentWithContactsList.add(studentWithContact)
            }while(cursor.moveToNext())
        }
        db.close()

        var contactDAO = ContactDAOSQLImpl(context)
        for (studentWithContacts in studentWithContactsList){
            studentWithContacts.contacts = contactDAO.getContacts(studentWithContact.student.id)
        }

        for (index in 0 until studentWithContactsList.size) {
            studentWithContactsList[index].contacts = contactDAO.getContacts(studentWithContactsList[index].student.id)
        }

        return studentWithContactsList
    }

    override fun getStudentsByLastNameAndFirstName(firstName: String, lastName: String) : ArrayList<Student> {
        val studentList: ArrayList<Student> = ArrayList()

        val columns = arrayOf(DatabaseHandler.studentFirstName,
            DatabaseHandler.studentlastName,
            DatabaseHandler.studentId,
            DatabaseHandler.yearstarted,
            DatabaseHandler.course)

        var databaseHandler:DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.query(DatabaseHandler.tableStudents,
                columns,
                "${DatabaseHandler.studentFirstName}=? and ${DatabaseHandler.studentlastName} = ? ", //added February 1 // PREPARED STATEMENTS
                arrayOf("$firstName", "$lastName"),
                null,
                null,
                DatabaseHandler.studentlastName
            )
        }catch (e: SQLiteException) {
            db.close()
            return ArrayList()
        }

        var student = Student()
        if (cursor.moveToFirst()){
            do {
                student = Student()
                student.id = cursor.getInt(2)
                student.lastName = cursor.getString(0)
                student.firstName = cursor.getString(1)
                student.yearstarted = cursor.getInt(3)
                studentList.add(student)
            }while(cursor.moveToNext())
        }
        db.close()
        return studentList
    }

}


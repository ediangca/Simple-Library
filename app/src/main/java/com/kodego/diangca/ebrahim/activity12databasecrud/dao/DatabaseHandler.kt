package com.kodego.diangca.ebrahim.activity12databasecrud.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASENAME, null, DATABASEVERSION) {


    companion object {
        private val DATABASEVERSION = 1
        private val DATABASENAME = "library"


        //Borrower
        val tableBorrower = "borrower"
        val borrowerId = "borrowerID"
        val borrowerFirstName = "firstname"
        val borrowerLastName = "lastname"
        val borrowerDepartment = "department"
        val borrowerContactNo = "contactNo"

        //Book
        val tableBook = "book"
        val bookId = "bookID"
        val book = "firstname"
        val bookdescription = "lastname"

        //Transaction
        val tableTransaction = "transactions"
        val transactionId = "TRXNo"
        val transactionBorrower = "borrowerID"
        val transactionBook = "bookID"
        val transactionDateBorrow = "DateBorrow"
        val transactionDateReturn = "DateReturn"


        val tableStudents = "student_table"
        val studentId = "id"
        val studentFirstName = "firstname"
        val studentlastName = "lastname"
        var yearstarted = "year_started"
        var course = "course"

        //added January 31,2023
        val tableContacts = "student_contacts"
        val contactID = "id"
        val studentcontactID = "student_id"
        val contactType = "contact_type"
        val contactDetails = "contact_details"

    }


/*    private var departments = arrayOf(
        "Select Department",
        "IIT (Information Technology)",
        "IMB (Marine Biology)",
        "IED (Education)",
        "IPA (Public Ad)",
        "IE (ENGINEERING"
    )*/

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATEBORROWERTABLE = "CREATE TABLE $tableBorrower " +
                "($borrowerId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$borrowerFirstName TEXT , " +
                "$borrowerLastName TEXT, " +
                "$borrowerDepartment TEXT , " +
                "$borrowerContactNo INTEGER)"
        db?.execSQL(CREATEBORROWERTABLE)

        db?.execSQL(
            "Insert into $tableBorrower ($borrowerFirstName, $borrowerLastName, $borrowerDepartment, $borrowerContactNo)values(" +
                    "'EBRAHIM', 'DIANGCA', 'IIT (Information Technology)', 09123456789)"
        )
        db?.execSQL(
            "Insert into $tableBorrower ($borrowerFirstName, $borrowerLastName, $borrowerDepartment, $borrowerContactNo)values(" +
                    "'ROSE MARIE', 'DIANGCA', 'IMB (Marine Biology)', 09123456789 )"
        )
        db?.execSQL(
            "Insert into $tableBorrower ($borrowerFirstName, $borrowerLastName, $borrowerDepartment, $borrowerContactNo)values(" +
                    "'MOHAMMAD RAFI', 'DIANGCA', 'IED (Education)', 09123456789 )"
        )
        db?.execSQL(
            "Insert into $tableBorrower ($borrowerFirstName, $borrowerLastName, $borrowerDepartment, $borrowerContactNo)values(" +
                    "'FARHANA', 'DIANGCA', 'IED (Education)', 09123456789 )"
        )
        db?.execSQL(
            "Insert into $tableBorrower ($borrowerFirstName, $borrowerLastName, $borrowerDepartment, $borrowerContactNo)values(" +
                    "'SACAR', 'DIANGCA', 'IIT (Information Technology)',  09123456789 )"
        )
        db?.execSQL(
            "Insert into $tableBorrower ($borrowerFirstName, $borrowerLastName, $borrowerDepartment, $borrowerContactNo)values(" +
                    "'DIANGCA', 'NAIMA', 'IE (ENGINEERING)', 09123456789 )"
        )
        db?.execSQL(
            "Insert into $tableBorrower ($borrowerFirstName, $borrowerLastName, $borrowerDepartment, $borrowerContactNo)values(" +
                    "'DIANGCA', 'ISMAEL', 'IE (ENGINEERING)', 09123456789)"
        )
        db?.execSQL(
            "Insert into $tableBorrower ($borrowerFirstName, $borrowerLastName, $borrowerDepartment, $borrowerContactNo)values(" +
                    "'DIANGCA', 'JAHARAH', 'IE (ENGINEERING)', 09123456789 )"
        )
        db?.execSQL(
            "Insert into $tableBorrower ($borrowerFirstName, $borrowerLastName, $borrowerDepartment, $borrowerContactNo)values(" +
                    "'DIANGCA', 'ASLIAH', 'IIT (Information Technology)', 09123456789 )"
        )


        /*val tableBook = "book"
        val bookId = "bookID"
        val book = "firstname"
        val description = "lastname"*/
        val CREATEBOOKTABLE = "CREATE TABLE $tableBook " +
                "($bookId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$book TEXT , " +
                "$bookdescription TEXT)"
        db?.execSQL(CREATEBOOKTABLE)

        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'ENGLISH 1', 'BASIC ENGLISH')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'ENGLISH 2', 'ADVANCED ENGLISH')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'MATH 1', 'ALGEBRA')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'MATH 2', 'TRIGONOMETRY')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'MATH 3', 'CALCULUS')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'ACCOUNTING 1', 'BASIC ACCOUNTING')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'ACCOUNTING 2', 'ADVANCED ACCOUNTING')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'STAT', 'STATISTICS')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'SCIENCE 1', 'BIOLOGY')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'SCIENCE 2', 'GEOLOGY')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'SCIENCE 3', 'CHEMISTRY')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'SCIENCE 4', 'PHYSICS')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'IT PROGRAMMING 1', 'FLOW CHART')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'IT PROGRAMMING 2', 'BASIC FUNDAMENTAL OF PROGRAMMING')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'IT PROGRAMMING 3', 'JAVA')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'IT PROGRAMMING 4', 'OOP')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'IT PROGRAMMING 5', 'DATABASE')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'IT PROGRAMMING 6', 'KOTLIN')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'IT PROGRAMMING 7', 'FLUTTER')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'IT PROGRAMMING 8', 'REST API')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'IT PROGRAMMING 9', 'GOOGLE FIREBASE')")
        db?.execSQL("Insert into $tableBook ($book, $bookdescription)values( 'IT PROGRAMMING 10', 'CAPSTONE PROJECTS')")

        /*val tableTransaction = "transaction"
        val transactionId = "TRXNo"
        val transactionBorrower = "borrowerID"
        val transactionBook = "bookID"
        val transactionDateBorrow = "DateBorrow"
        val transactionDateReturn = "DateReturn"*/

        val CREATETRANSACTIONTABLE = "CREATE TABLE $tableTransaction " +
                "($transactionId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$transactionBorrower TEXT , " +
                "$transactionBook TEXT , " +
                "$transactionDateBorrow DATE , " +
                "$transactionDateReturn DATE)"
        db?.execSQL(CREATETRANSACTIONTABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $tableBorrower")
        onCreate(db)
    }


}

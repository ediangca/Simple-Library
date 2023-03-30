package com.kodego.diangca.ebrahim.activity12databasecrud.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.support.v4.app.INotificationSideChannel
import android.util.Log
import android.widget.Toast
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Borrower

interface BorrowerDAO {

    fun addBorrower(borrower: Borrower)
    fun getBorrowers() : ArrayList<Borrower>
    fun updateBorrower(borrowerId: Int, borrower: Borrower)
    fun deleteBorrower(borrowerId:String)
    fun existBorrower(borrowerId: String, firstName: String, lastName: String):Boolean
    fun searchBorrowerByFilter(searchString: String) : ArrayList<Borrower>
    fun searchBorrowerByDepartmentWithFilter(department: String, searchString: String) : ArrayList<Borrower>

}

class BorrowerDAOSQLImp(var context: Context): BorrowerDAO{

    override fun addBorrower(borrower: Borrower) {

        Log.d("BORROWER_DAO", "ADDING BORROWER ${borrower.getDetails()}")
        var databaseHandler: DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(DatabaseHandler.borrowerFirstName, borrower.firstName)
        contentValues.put(DatabaseHandler.borrowerLastName, borrower.lastName)
        contentValues.put(DatabaseHandler.borrowerDepartment, borrower.department)
        contentValues.put(DatabaseHandler.borrowerContactNo, borrower.contactNo)

        val success = db.insert(DatabaseHandler.tableBorrower, null, contentValues)
        if(success > -1){
            Toast.makeText(context, "Borrower successfully added!", Toast.LENGTH_SHORT).show()
            db.close()
        }
    }

    override fun getBorrowers(): ArrayList<Borrower> {
        val borrowerList: ArrayList<Borrower> = ArrayList()

        val query = "SELECT  ${DatabaseHandler.borrowerId}, " +
                "${DatabaseHandler.borrowerFirstName}, " +
                "${DatabaseHandler.borrowerLastName}, " +
                "${DatabaseHandler.borrowerDepartment}, " +
                "${DatabaseHandler.borrowerContactNo} " +
                "FROM ${DatabaseHandler.tableBorrower}"

        var databaseHandler: DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var cursor: Cursor? = null

//        cursor = db.rawQuery(query, arrayOf("${DatabaseHandler.studentId}, ${DatabaseHandler.studentLastName}, ${DatabaseHandler.studentFirstName}"))
        try {
            cursor = db.rawQuery(query, null)
        }catch (sqlExemption : SQLiteException){
            db.close()
            return ArrayList()
        }
        var borrower: Borrower

        if(cursor.moveToFirst()){
            do{
                borrower = Borrower(cursor.getString(1), cursor.getString(2))
                borrower.borrowerId = cursor.getString(0)
                borrower.department = cursor.getString(3)
                borrower.contactNo = cursor.getInt(4)

                borrowerList.add(borrower)

            }while (cursor.moveToNext())
        }

        db.close()

        return borrowerList
    }

    override fun updateBorrower(borrowerId: Int, borrower: Borrower) {
        var databaseHandler:DatabaseHandler = DatabaseHandler(context)
        var db = databaseHandler.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(DatabaseHandler.borrowerFirstName, borrower.firstName)
        contentValues.put(DatabaseHandler.borrowerLastName, borrower.lastName)
        contentValues.put(DatabaseHandler.borrowerDepartment, borrower.department)
        contentValues.put(DatabaseHandler.borrowerContactNo, borrower.contactNo)

        val values = arrayOf("$borrowerId")

        val success = db.update(DatabaseHandler.tableStudents,
            contentValues,
            "${DatabaseHandler.borrowerId} = ?",
            values)
        db.close()
    }

    override fun deleteBorrower(borrowerId: String) {
        var databaseHandler: DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.writableDatabase

        val values = arrayOf("$borrowerId")
        val success = db.delete(
            DatabaseHandler.tableBorrower,
            "${DatabaseHandler.borrowerId} = ?",
            values
        )
        db.close()
    }

    override fun existBorrower(borrowerId: String, firstName: String, lastName: String): Boolean {

        var count: Int = 0

        val query = "SELECT  count(*) as NoOfEntry " +
                "FROM ${DatabaseHandler.tableBorrower} " +
                "WHERE ${DatabaseHandler.borrowerId} = '$borrowerId' OR " +
                "(${DatabaseHandler.borrowerFirstName} = '$firstName' AND ${DatabaseHandler.borrowerLastName} = '$lastName')"

        Log.d("BORROWER_DAO_EXIST", "$query")

        var databaseHandler:DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(query, null)
        }catch (e: SQLiteException) {
            db.close()
            true
        }

        var borrower = Borrower()
        if (cursor!!.moveToFirst()){
            do {
                count = cursor.getInt(0)
            }while (cursor.moveToNext())
        }

        db.close()
        Log.d("BORROWER_DAO_COUNT", "$count")

        return count >  0

    }

    override fun searchBorrowerByDepartmentWithFilter(department:String, searchString: String): ArrayList<Borrower> {
        val borrowerList: ArrayList<Borrower> = ArrayList()

        val query = "SELECT  ${DatabaseHandler.borrowerId}, " +
                "${DatabaseHandler.borrowerFirstName}, " +
                "${DatabaseHandler.borrowerLastName}, " +
                "${DatabaseHandler.borrowerDepartment}, " +
                "${DatabaseHandler.borrowerContactNo} " +
                "FROM ${DatabaseHandler.tableBorrower} WHERE ${DatabaseHandler.borrowerDepartment} ='$department' and " +
                "(${DatabaseHandler.borrowerFirstName} like '%$searchString%' OR ${DatabaseHandler.borrowerLastName} like '%$searchString%')"

        var databaseHandler: DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(query, null)
        }catch (sqlExemption : SQLiteException){
            db.close()
            return ArrayList()
        }
        var borrower: Borrower

        if(cursor.moveToFirst()){
            do{
                borrower = Borrower(cursor.getString(1), cursor.getString(2))
                borrower.borrowerId = cursor.getString(0)
                borrower.department = cursor.getString(3)
                borrower.contactNo = cursor.getInt(4)

                borrowerList.add(borrower)

            }while (cursor.moveToNext())
        }

        db.close()

        return borrowerList
    }
    override fun searchBorrowerByFilter(searchString: String): ArrayList<Borrower> {
        val borrowerList: ArrayList<Borrower> = ArrayList()

        val query = "SELECT  ${DatabaseHandler.borrowerId}, " +
                "${DatabaseHandler.borrowerFirstName}, " +
                "${DatabaseHandler.borrowerLastName}, " +
                "${DatabaseHandler.borrowerDepartment}, " +
                "${DatabaseHandler.borrowerContactNo} " +
                "FROM ${DatabaseHandler.tableBorrower} WHERE ${DatabaseHandler.borrowerFirstName} like '%$searchString%' OR ${DatabaseHandler.borrowerLastName} like '%$searchString%'"

        var databaseHandler: DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(query, null)
        }catch (sqlExemption : SQLiteException){
            db.close()
            return ArrayList()
        }
        var borrower: Borrower

        if(cursor.moveToFirst()){
            do{
                borrower = Borrower(cursor.getString(1), cursor.getString(2))
                borrower.borrowerId = cursor.getString(0)
                borrower.department = cursor.getString(3)
                borrower.contactNo = cursor.getInt(4)

                borrowerList.add(borrower)

            }while (cursor.moveToNext())
        }

        db.close()

        return borrowerList
    }

}
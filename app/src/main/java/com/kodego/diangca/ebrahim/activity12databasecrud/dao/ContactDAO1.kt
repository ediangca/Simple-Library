package com.kodego.diangca.ebrahim.activity12databasecrud.dao


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.provider.ContactsContract
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Contact
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Student

interface ContactDAO1 {

    fun addContact(contact: Contact)
    fun addContacts(contacts: ArrayList<Contact>)
    fun getContacts(studentID: Int): ArrayList<Contact>
    fun updateContact(contact: Contact)
    fun deleteContact(studentID: Int)

}


class ContactDAOSQLImpl(var context: Context) : ContactDAO {
    override fun addContact(contact: Contact) {
        var databaseHandler: DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(DatabaseHandler.studentcontactID, contact.studentID)
        contentValues.put(DatabaseHandler.contactType, contact.contactType)
        contentValues.put(DatabaseHandler.contactDetails, contact.contactDetails)

        val success = db.insert(DatabaseHandler.tableContacts, null, contentValues)
        db.close()

    }

    override fun addContacts(contacts: ArrayList<Contact>) {
        for (contact in contacts) {
            addContact(contact)
        }
    }

    override fun getContacts(studentID: Int): ArrayList<Contact> {
        val contactList: ArrayList<Contact> = ArrayList()  //declared the data to be returned
        var databaseHandler: DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var cursor: Cursor? = null

        val columns = arrayOf(
            DatabaseHandler.contactID,
            DatabaseHandler.studentcontactID,
            DatabaseHandler.contactType,
            DatabaseHandler.contactDetails
        )

        try {
            cursor = db.query(
                DatabaseHandler.tableStudents,
                columns,
                "${DatabaseHandler.studentcontactID} = '${studentID}'",
                null,
                null,
                null,
                null,
            )
        } catch (e: SQLiteException) {
            db.close()
            return ArrayList()
        }

        var contact = Contact()
        if (cursor.moveToFirst()) {
            do {
                contact = Contact()
                contact.id = cursor.getInt(0)
                contact.studentID = cursor.getInt(1)
                contact.contactType = cursor.getString(2)
                contact.contactDetails = cursor.getString(3)
                contactList.add(contact)
            } while (cursor.moveToNext())
        }

        db.close()
        return contactList
    }

    override fun updateContact(contact: Contact) {
        var databaseHandler: DatabaseHandler = DatabaseHandler(context)
        var db = databaseHandler.writableDatabase // Open database as writable

        val contentValues = ContentValues()
        contentValues.put(DatabaseHandler.studentcontactID, contact.studentID)
        contentValues.put(DatabaseHandler.contactType, contact.contactType)
        contentValues.put(DatabaseHandler.contactDetails, contact.contactDetails)

        val values = arrayOf("${contact.id}")
        val success = db.update(
            DatabaseHandler.tableContacts,
            contentValues,
            "${DatabaseHandler.contactID} = ?",
            values
        )
        db.close()

    }

    override fun deleteContact(studentID: Int) {
        var databaseHandler: DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.writableDatabase

        val values = arrayOf("$studentID")
        val success = db.delete(
            DatabaseHandler.tableStudents,
            "${DatabaseHandler.contactID} = ?",
            values
        )
        db.close()
    }

}

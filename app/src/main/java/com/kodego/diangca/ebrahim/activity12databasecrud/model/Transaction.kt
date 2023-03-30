package com.kodego.diangca.ebrahim.activity12databasecrud.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Transaction(var book: Book? = null, var borrower: Borrower? = null): Parcelable {
    var TRXNo :Int = 1
    var status = Status.PENDING
    var dateBorrowed = Date()
    var dateReturn = Date()

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Book::class.java.classLoader),
        parcel.readParcelable(Borrower::class.java.classLoader)
    ) {
        TRXNo = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(book, flags)
        parcel.writeParcelable(borrower, flags)
        parcel.writeInt(TRXNo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaction> {
        override fun createFromParcel(parcel: Parcel): Transaction {
            return Transaction(parcel)
        }

        override fun newArray(size: Int): Array<Transaction?> {
            return arrayOfNulls(size)
        }
    }


}

enum class Status{
    PENDING,
    RETURNED
}
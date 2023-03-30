package com.kodego.diangca.ebrahim.activity12databasecrud.model

import android.os.Parcel
import android.os.Parcelable

class Book(
    var bookName: String? = "",
    var description: String? = "",
) : Parcelable {

    var bookID :Int = 1

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
        bookID = parcel.readInt()
    }

    fun getDetails(): String {
        return "$bookName ($description)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bookName)
        parcel.writeString(description)
        parcel.writeInt(bookID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}


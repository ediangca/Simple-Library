package com.kodego.diangca.ebrahim.activity12databasecrud.model

import android.os.Parcel
import android.os.Parcelable


open class User(var username: String?, var password: String?): Parcelable {
    var email: String? = null
    var type = userType.UNKNOWN

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
        email = parcel.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(username)
        dest.writeString(password)
        dest.writeString(email)
    }


    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

    fun details(): String {
        return "User(username=$username, password=$password, email=$email, type=$type)"
    }

}

enum class userType {
    ADMIN,
    LIBRARIAN,
    STUDENT,
    UNKNOWN
}
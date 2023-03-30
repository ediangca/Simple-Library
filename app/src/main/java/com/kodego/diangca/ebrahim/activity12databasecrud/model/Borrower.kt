package com.kodego.diangca.ebrahim.activity12databasecrud.model

import android.os.Parcel
import android.os.Parcelable
import com.kodego.diangca.ebrahim.activity12databasecrud.R

class Borrower(
    var firstName: String? = "",
    var lastName: String? = "", val img: Int = R.drawable.profile_icon
):Parcelable {

    var borrowerId: String? = "0"
    var contactNo: Int = 0
    var department: String? = "UNKNOWN"

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
        borrowerId = parcel.readString()
        contactNo = parcel.readInt()
        department = parcel.readString()
    }

    constructor():this("","", R.drawable.profile_icon){
    }

    fun getDetails(): String {
        return "$borrowerId - ($lastName, $firstName)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeInt(img)
        parcel.writeString(borrowerId)
        parcel.writeInt(contactNo)
        parcel.writeString(department)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Borrower> {
        override fun createFromParcel(parcel: Parcel): Borrower {
            return Borrower(parcel)
        }

        override fun newArray(size: Int): Array<Borrower?> {
            return arrayOfNulls(size)
        }
    }


}


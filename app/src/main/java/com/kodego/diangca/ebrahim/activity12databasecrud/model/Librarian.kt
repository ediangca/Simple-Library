package com.kodego.diangca.ebrahim.activity12databasecrud.model

import android.os.Parcelable
import com.kodego.diangca.ebrahim.activity12databasecrud.R

class Librarian(
    var firstName: String? = null,
    var lastName: String? = null, val img: Int = R.drawable.profile_icon
) : User("",""), Parcelable {

}


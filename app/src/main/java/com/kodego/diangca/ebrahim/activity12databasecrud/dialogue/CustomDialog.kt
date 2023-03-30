package com.kodego.diangca.ebrahim.activity12databasecrud.dialogue

import android.app.Dialog
import android.content.Context

class CustomDialog (context: Context) :Dialog(context) {

    init {
        dialog = Dialog(context)
    }

    companion object{

    var dialog: Dialog? = null

 }
}



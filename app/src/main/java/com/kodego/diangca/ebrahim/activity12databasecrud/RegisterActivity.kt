package com.kodego.diangca.ebrahim.activity12databasecrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.kodego.diangca.ebrahim.activity12databasecrud.databinding.ActivityRegisterBinding
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Borrower
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Librarian
import com.kodego.diangca.ebrahim.activity12databasecrud.model.User
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private var users: ArrayList<User> = ArrayList()
    private lateinit var librarian: Librarian
    private var lastName: String? = null
    private var firstName: String? = null
    private var username: String? = null
    private var password: String? = null
    private var email: String? = null
    private var question: Int? = null
    private var answer: String? = null

    private lateinit var lastActivity: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initComponent()

    }

    private fun initComponent() {

        if (intent!=null) {
            Log.d("REGISTER_EXTRA", "GET PARCELABLE EXTRA")
            lastActivity = intent.getStringExtra("reference")!!
            users = intent?.getParcelableArrayListExtra("users")!!
        }

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            btnRegisterClickListener()
        }
        binding.btnBack.setOnClickListener {
            btnBackClickListener()
        }
    }

    private fun btnBackClickListener() {
        var intent: Intent? = null
        Log.d("REGISTER_EXTRA", lastActivity)
        if (lastActivity.equals("MENU", true)) {
            var intent = Intent(this, MenuActivity::class.java)
            intent.putParcelableArrayListExtra("users", users)
            startActivity(intent)
            finish()
        } else {
            var intent = Intent(this, LoginActivity::class.java)
            intent.putParcelableArrayListExtra("users", users)
            startActivity(intent)
            finish()
        }

    }

    private fun btnRegisterClickListener() {
        lastName = binding.lastName.text.toString()
        firstName = binding.firstName.text.toString()
        username = binding.username.text.toString()
        password = binding.password.text.toString()
        email = binding.email.text.toString()
        question = binding.questionList.selectedItemId.toInt()
        answer = binding.answer.text.toString()

        if (checkFields()) {
            librarian = Librarian(lastName, firstName)
            librarian.username = username
            librarian.password = password
            librarian.email = email
            val intent = Intent()
            intent.putExtra("student", librarian)
            setResult(1, intent)
            finish()
        }
    }

    private fun checkFields(): Boolean {
        var valid = true
        var promptMessage: String = "Please check empty fields."
        var promptTextView: TextView = TextView(applicationContext)


        if (lastName.isNullOrEmpty() ||
            firstName.isNullOrEmpty() ||
            username.isNullOrEmpty() ||
            password.isNullOrEmpty() ||
            email.isNullOrEmpty() ||
            question==0 ||
            answer.isNullOrEmpty()
        ) {
            valid = false
        }
        if (!promptMessage.isNullOrEmpty()) {
            Snackbar.make(binding.root, promptMessage, Snackbar.LENGTH_SHORT).show()
        }
        return valid
    }


}
package com.kodego.diangca.ebrahim.activity12databasecrud

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.kodego.diangca.ebrahim.activity12databasecrud.databinding.ActivityLoginBinding
import com.kodego.diangca.ebrahim.activity12databasecrud.model.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class LoginActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: User
    private var users: ArrayList<User> = ArrayList()

    @SuppressLint("SimpleDateFormat")
    private var dateFormat = SimpleDateFormat("yyyy-MM-d")

    private val launchRegister = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data

        user = data!!.extras?.getParcelable("student")!!
        Log.d("LOGIN_ADD_USER", user.email.toString())
        users.add(user)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initComponent()

    }

    private fun initComponent() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent!=null) {
            Log.d("LOGIN_EXTRA", "GET PARCELABLE EXTRA")
            users = intent?.getParcelableArrayListExtra("users")!!
        }

        binding.btnForgotPass.setOnClickListener {
            btnForgotPassClickListener()
        }
        binding.btnSubmit.setOnClickListener {
            btnSubmitClickListener()
        }
        binding.btnRegister.setOnClickListener {
            btnRegisterClickListener()
        }

        showUserList()
    }

    private fun showUserList() {

        Log.d("USER_LIST", "NO. OF USERS ${users.size}")
        for (u in users) {
            println("USER_INFO ${u.details()}")
        }
    }

    private fun btnRegisterClickListener() {
        val intentRegister = Intent(this, RegisterActivity::class.java)
        intentRegister.putExtra("reference", "LOGIN")
        intentRegister.putParcelableArrayListExtra("users", users)
        launchRegister.launch(intentRegister)
    }

    private fun btnSubmitClickListener() {
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Either Username or Password is Invalid. \n Please try again!",
                Toast.LENGTH_SHORT
            ).show()
        } else {

            if (validateUser(username, password)) {
                var nextForm = Intent(this, MainActivity::class.java)
                var bundle = Bundle()
                bundle.putString("username", binding.username.text.toString())
                bundle.putString("password", binding.password.text.toString())
                nextForm.putExtras(bundle)

                nextForm.putExtra("dateLog", dateFormat.format(Date()))

                startActivity(Intent(nextForm))

                finish()
            }else{
                Toast.makeText(
                    applicationContext,
                    "Either Username or Password is Invalid. \n Please try again!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun validateUser(username: String, password: String): Boolean {
        Log.d("USER_VALIDATING", "$username, $password")
        for(u in users){
            println("FETCH_USER ${u.details()}")
            if(u.username.equals(username) && u.password.equals(password)){
                Log.d("USER_ADAPTER_FOUND", "${u.toString()}")
                return true
            }
        }
        return false
    }

    private fun btnForgotPassClickListener() {
        var nextForm = Intent(this, ForgotPassActivity::class.java)
        nextForm.putExtra("something", dateFormat.format(Date()))
        startActivity(Intent(nextForm))
//        finish()
    }
}
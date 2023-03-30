package com.kodego.diangca.ebrahim.activity12databasecrud

/**
Add functionality to

Add
Edit
Update
Delete



from the database of (Books, Grocery, or Student)

1 table lang lalagyan ninyo

 * */

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.kodego.diangca.ebrahim.activity12databasecrud.adapter.BorrowerAdapter
import com.kodego.diangca.ebrahim.activity12databasecrud.databinding.ActivityMenuBinding
import com.kodego.diangca.ebrahim.activity12databasecrud.model.*
import kotlin.collections.ArrayList

class MenuActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMenuBinding
    private lateinit var userAdapter: BorrowerAdapter
    private lateinit var user: User
    private var users: ArrayList<User> = ArrayList()

    private var books: ArrayList<Book> =  ArrayList()

    private val launchRegister = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data

        user = data!!.extras?.getParcelable("student")!!
        Log.d("MENU_ADD_USER", user.email.toString())
        users.add(user)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initComponent()

    }

    private fun initComponent() {

        if (intent!=null) {
            Log.d("MENU_EXTRA", "GET PARCELABLE EXTRA")
            try {
                users = intent?.getParcelableArrayListExtra("users")!!
            }catch (e: Exception){
                Log.d("MENU_EXCEPTION", e.message.toString())
            }
        }

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            btnLoginClickListener()
        }
        binding.btnRegister.setOnClickListener {
            btnRegisterClickListener()
        }

        dummyStudents()
    }

    private fun dummyStudents() {
        user = Librarian("Sacar", "Diangca", R.drawable.profile_icon)
        user.username = "admin1"
        user.password = "admin1"
        user.email = "sacarDiangca@gmail.com"
        user.type = userType.ADMIN
        users.add(user)
        user = Librarian("Name", "Diangca", R.drawable.profile_icon)
        user.username = "admin2"
        user.password = "admin2"
        user.email = "naimaDiangca@gmail.com"
        user.type = userType.ADMIN
        users.add(user)
        user = Librarian("Ebrahim", "Diangca", R.drawable.profile_icon)
        user.username = "admin3"
        user.password = "admin3"
        user.email = "ebrahimDiangca@gmail.com"
        user.type = userType.ADMIN

        users.add(user)

    }

    private fun btnRegisterClickListener() {

        val intentRegister = Intent(this, RegisterActivity::class.java)
        intentRegister.putExtra("reference", "MENU")
        intentRegister.putParcelableArrayListExtra("users", users)
        launchRegister.launch(intentRegister)

    }

    private fun btnLoginClickListener() {

        var intentLogin = Intent(this, LoginActivity::class.java)
        intentLogin.putParcelableArrayListExtra("users", users)
        startActivity(intentLogin)
        finish()

    }
}
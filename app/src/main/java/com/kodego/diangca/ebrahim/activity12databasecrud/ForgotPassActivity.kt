package com.kodego.diangca.ebrahim.activity12databasecrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kodego.diangca.ebrahim.activity12databasecrud.databinding.ActivityForgotPassBinding
import com.kodego.diangca.ebrahim.activity12databasecrud.databinding.ActivityMainBinding

class ForgotPassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            btnSubmitClickListener()
        }
        binding.btnBack.setOnClickListener {
            btnBackClickListener()
        }

    }

    private fun btnSubmitClickListener() {
        Toast.makeText(
            applicationContext,
            "Great! \n Please check your email.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun btnBackClickListener() {
        var nextForm = Intent(this, MenuActivity::class.java)
        startActivity(Intent(nextForm))
        finish()
    }
}
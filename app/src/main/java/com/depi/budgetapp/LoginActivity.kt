package com.depi.budgetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    lateinit var asign:TextView
    lateinit var aback:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        asign=findViewById(R.id.sign_tv)
        aback=findViewById(R.id.back_btn)

        asign.setOnClickListener(View.OnClickListener {
            val intent= Intent(this,sigupActivity::class.java)
            startActivity(intent)
        })
        aback.setOnClickListener(View.OnClickListener {
            finish()
        })
    }
}
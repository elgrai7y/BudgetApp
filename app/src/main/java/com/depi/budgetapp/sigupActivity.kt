package com.depi.budgetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class sigupActivity : AppCompatActivity() {
    lateinit var alogin: TextView
    lateinit var aback: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigup)
        alogin=findViewById(R.id.login_tv)
        aback=findViewById(R.id.back_btn)

        alogin.setOnClickListener(View.OnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        })
        aback.setOnClickListener(View.OnClickListener {
           finish()
        })
    }
}
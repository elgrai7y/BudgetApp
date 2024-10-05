package com.depi.budgetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    lateinit var aloginbtn: CardView
    lateinit var asignbtn: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        aloginbtn=findViewById(R.id.login_btn)
        asignbtn=findViewById(R.id.sign_btn)

        aloginbtn.setOnClickListener(View.OnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        })

        asignbtn.setOnClickListener(View.OnClickListener {
            val intent= Intent(this,sigupActivity::class.java)
            startActivity(intent)
        })
    }
}
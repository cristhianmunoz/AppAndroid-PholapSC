package com.munozcristhian.pholapsc

import android.content.Intent
import android.os.Bundle

import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SingInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in)
        var btnSingIn = findViewById<Button>(R.id.btnRegistrar)
        btnSingIn.setOnClickListener {
            val intention = Intent(this, ConfiguracionesActivity::class.java)
            startActivity(intention)
        }
        var btnGoLogin = findViewById<Button>(R.id.btnGoLogin)
        btnGoLogin.setOnClickListener {
            val intention1 = Intent(this,MainActivity::class.java)
            startActivity(intention1)
        }
        var btnbackSingIn = findViewById<ImageView>(R.id.imgViewBackBarSingIn)
        btnbackSingIn.setOnClickListener {
            val intention2 = Intent(this, MainActivity::class.java)
            startActivity(intention2)
        }

    }
}
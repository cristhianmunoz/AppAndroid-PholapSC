package com.munozcristhian.pholapsc

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ListSessionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_sesiones)
        /*
        var btnGoSession = findViewById<TextView>(R.id.textViewListSession_Session)
        btnGoSession.setOnClickListener {
            val intention = Intent(this, SessionActivity::class.java)
            startActivity(intention)
        }
        var btnGoImage = findViewById<TextView>(R.id.textViewListSessionFotos)
        btnGoImage.setOnClickListener {
            val intention1 = Intent(this, SeleccionActivity::class.java)
            startActivity(intention1)
        }*/

    }
}
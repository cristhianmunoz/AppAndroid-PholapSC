package com.munozcristhian.pholapsc

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView

class SessionActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session)
        var btnbackSession = findViewById<ImageView>(R.id.imgViewBackBarSession)
        btnbackSession.setOnClickListener {
            val intention = Intent(this, ListSessionActivity::class.java)
            startActivity(intention)
        }

    }
}
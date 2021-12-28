package com.munozcristhian.pholapsc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar

class Perfil_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        var toolBarPerfil = findViewById<Toolbar>(R.id.toolBarPerfil)
        setSupportActionBar(toolBarPerfil)
    }
}
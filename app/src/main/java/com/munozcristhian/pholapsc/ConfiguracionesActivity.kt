package com.munozcristhian.pholapsc
import android.annotation.SuppressLint
import android.content.Intent

import android.os.Bundle
import androidx.core.content.ContextCompat
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ConfiguracionesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuraciones)
        var btnBack = findViewById<ImageView>(R.id.imgViewBackConfiguraciones)
        var btnPerfil = findViewById<Button>(R.id.btnPerfilConfiguraciones)
        btnPerfil.setOnClickListener {
            val intention = Intent(this, Perfil_Activity::class.java)
            startActivity(intention)
        }
        btnBack.setOnClickListener {
            var intent = Intent(this,CategoryActivity::class.java)
            startActivity(intent)
        }

    }
}
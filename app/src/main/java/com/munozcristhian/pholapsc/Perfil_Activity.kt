package com.munozcristhian.pholapsc
import android.content.Intent

import android.os.Bundle

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Perfil_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        var btnBackPerfilConfiguracion = findViewById<ImageView>(R.id.imgViewBackBarPerfil)
        btnBackPerfilConfiguracion.setOnClickListener{
            val intention = Intent(this, ConfiguracionesActivity::class.java)
            startActivity(intention)
        }
    }
}
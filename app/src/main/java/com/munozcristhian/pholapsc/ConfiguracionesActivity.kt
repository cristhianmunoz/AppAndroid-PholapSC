package com.munozcristhian.pholapsc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.munozcristhian.pholapsc.databinding.ActivityConfiguracionesBinding
import com.munozcristhian.pholapsc.model.Usuario

class ConfiguracionesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfiguracionesBinding
    private lateinit var usuario: Usuario


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null) {
            usuario = extras.get(CURRENT_USER) as Usuario
        }else{
            Log.d("CONFIGURACIONES_LOG", "No hay extras para Configuraciones")
        }

        binding.btnPerfilConfiguraciones.setOnClickListener {
            val intention = Intent(this, Perfil_Activity::class.java)
            intention.putExtra(CURRENT_USER, usuario)
            startActivity(intention)
        }
        binding.imgViewBackConfiguraciones.setOnClickListener {
            val intent = Intent(this,CategoryActivity::class.java)
            startActivity(intent)
        }

    }
}
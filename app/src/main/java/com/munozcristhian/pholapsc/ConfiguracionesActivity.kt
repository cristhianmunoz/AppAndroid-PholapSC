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
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null) {
            usuario = extras.get(CURRENT_USER) as Usuario
            uid = extras.getString(UID_USER) as String
        }else{
            Log.d("CONFIGURACIONES_LOG", "No hay extras para Configuraciones")
        }

        binding.btnPerfilConfiguraciones.setOnClickListener {
            val intent = Intent(this, Perfil_Activity::class.java)
            intent.putExtra(CURRENT_USER, usuario)
            intent.putExtra(UID_USER, uid)
            startActivity(intent)
        }
        binding.imgViewBackConfiguraciones.setOnClickListener {
            val intent = Intent(this,CategoryActivity::class.java)
            intent.putExtra(CURRENT_USER, usuario)
            intent.putExtra(UID_USER, uid)
            startActivity(intent)
        }

    }
}
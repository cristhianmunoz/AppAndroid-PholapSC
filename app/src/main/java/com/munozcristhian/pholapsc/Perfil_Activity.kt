package com.munozcristhian.pholapsc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.munozcristhian.pholapsc.databinding.ActivityPerfilBinding
import com.munozcristhian.pholapsc.model.Usuario

class Perfil_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null) {
            usuario = extras.get(CURRENT_USER) as Usuario
            ingresarDatosUsuario()
        }else{
            Log.d("PERFIL_LOG", "No hay extras para Perfil")
        }

        // Back
        binding.imgViewBackBarPerfil.setOnClickListener{
            val intention = Intent(this, ConfiguracionesActivity::class.java)
            startActivity(intention)
        }
    }

    private fun ingresarDatosUsuario() {
        val text = usuario.nombre + " " + usuario.apellido
        binding.txtViewBarNombrePerfil.text = text
        binding.editTxtNombreUsuario.setText(text)
        binding.editTxtEmailPerfil.setText(usuario.correo)
        binding.editTxtPhonePerfil.setText(usuario.telefono)
        binding.editTxtLocation.setText(usuario.direccion)
        binding.txtViewGeneroPerfil.text = usuario.genero
    }
}
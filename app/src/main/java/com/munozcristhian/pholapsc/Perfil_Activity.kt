package com.munozcristhian.pholapsc

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.munozcristhian.pholapsc.databinding.ActivityPerfilBinding
import com.munozcristhian.pholapsc.model.Usuario

class Perfil_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding
    private lateinit var usuario: Usuario
    private lateinit var uid: String
    private lateinit var realtimeDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        comprobar()

        // Back
        binding.imageButtonBack.setOnClickListener{
            val intent = Intent(this, ConfiguracionesActivity::class.java)
            intent.putExtra(CURRENT_USER, usuario)
            intent.putExtra(UID_USER, uid)
            startActivity(intent)
        }
        binding.imageButtonEdit.setOnClickListener{
            val intent = Intent(this, PerfilEditActivity::class.java)
            intent.putExtra(CURRENT_USER, usuario)
            intent.putExtra(UID_USER, uid)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        comprobar()
    }

    private fun comprobar(){

        val extras = intent.extras
        if (extras != null) {
            uid = extras.getString(UID_USER) as String

                // Initialize Realtime Database
                realtimeDatabase = FirebaseDatabase.getInstance().reference.child("Usuarios")
                realtimeDatabase.child(uid).get().addOnSuccessListener {
                    Log.i("FIREBASE_REALTIME_DATABASE", "Got value $it")
                    usuario = Usuario(
                        it.child("nombre").value.toString(),
                        it.child("apellido").value.toString(),
                        it.child("genero").value.toString(),
                        it.child("telefono").value.toString(),
                        it.child("direccion").value.toString(),
                        it.child("correo").value.toString()
                    )
                ingresarDatosUsuario()
                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                }
        }else{
            Log.d("PERFIL_LOG", "No hay extras para Perfil")
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
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
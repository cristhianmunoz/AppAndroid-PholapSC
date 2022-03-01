package com.munozcristhian.pholapsc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.munozcristhian.pholapsc.databinding.ActivityPerfilEditBinding
import com.munozcristhian.pholapsc.model.Usuario
import com.munozcristhian.pholapsc.ui.main.SpinnerAdapter

class PerfilEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilEditBinding
    private lateinit var usuario: Usuario
    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var uid: String
    private lateinit var auth: FirebaseAuth
    private lateinit var realtimeDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth

        // Initialize Realtime Database
        realtimeDatabase = FirebaseDatabase.getInstance().reference.child("Usuarios")

        val extras = intent.extras
        if (extras != null) {
            usuario = extras.get(CURRENT_USER) as Usuario
            uid = extras.getString(UID_USER) as String
            ingresarDatosUsuario()
        }else{
            Log.d("PERFIL_LOG", "No hay extras para Perfil")
        }

        // Generos
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(this, R.array.generos, android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinnerGeneros.adapter = adapter
        }
        spinnerAdapter = SpinnerAdapter()
        binding.spinnerGeneros.onItemSelectedListener = spinnerAdapter

        // Back
        binding.imageButtonBackPerfil.setOnClickListener{
            val intent = Intent(this, Perfil_Activity::class.java)
            intent.putExtra(CURRENT_USER, usuario)
            intent.putExtra(UID_USER, uid)
            startActivity(intent)
        }
        binding.imageButtonSaveUpdate.setOnClickListener{

            if(validar()){
                val intent = Intent(this, Perfil_Activity::class.java)
                intent.putExtra(CURRENT_USER, usuario)
                intent.putExtra(UID_USER, uid)
                startActivity(intent)
            }


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
        if (usuario.genero=="Mujer"){
            binding.spinnerGeneros.setSelection(0)
        }
        //binding.spinnerGeneros.dropDownVerticalOffset= usuario.genero
    }
    private fun validar (): Boolean{
        // Validar correo
        if(!isValidEmail(binding.editTxtEmailPerfil.text)){
            binding.editTxtEmailPerfil.error = resources.getString(R.string.email_no_valid)
            return false
        }
        // Validar nombre y apellido
        if(binding.editTxtNombreUsuario.text.isEmpty()){
            binding.editTxtNombreUsuario.error = resources.getString(R.string.nombre_requerido)
            return false
        }
        //Validar telefono
        if(binding.editTxtPhonePerfil.text.length != 10){
            binding.editTxtPhonePerfil.error = resources.getString(R.string.telefono_requerido)
            return false
        }

        // Validar direccion
        if(binding.editTxtLocation.text.isEmpty()){
            binding.editTxtLocation.error = resources.getString(R.string.direccion_requerido)
            return false
        }

        // Datos
        //-----------nombre and apellido---------
        val nombre_apellido = binding.editTxtNombreUsuario.text.toString()
        val delim = " "
        val lista = nombre_apellido.split(delim)
        //--------------------------------------
        val nombre = lista[0]
        val apellido = lista[1]
        val genero = binding.spinnerGeneros.selectedItem.toString()
        val telefono = binding.editTxtPhonePerfil.text.toString()
        val direction = binding.editTxtLocation.text.toString()
        val correo = binding.editTxtEmailPerfil.text.toString()

        val usuario = Usuario(nombre, apellido, genero, telefono, direction, correo)

        // Authentication
        val UID_USER = auth.uid.toString()

        // Realtime Database
        realtimeDatabase.child(UID_USER).setValue(usuario).addOnSuccessListener {
            Toast.makeText(baseContext, "Los datos del usuario se han guardado con exito", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(baseContext, "No se pudo guardar los datos del usuario", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun isValidEmail(target: CharSequence): Boolean{
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}
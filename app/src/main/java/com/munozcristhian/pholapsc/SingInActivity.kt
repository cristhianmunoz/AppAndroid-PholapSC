package com.munozcristhian.pholapsc

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.munozcristhian.pholapsc.databinding.ActivitySingInBinding
import com.munozcristhian.pholapsc.model.Usuario
import com.munozcristhian.pholapsc.ui.main.SpinnerAdapter

class SingInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySingInBinding
    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var realtimeDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Initialize Realtime Database
        realtimeDatabase = FirebaseDatabase.getInstance().reference.child("Usuarios")

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

        // Registrar
        binding.btnRegistrar.setOnClickListener {
            // Validar nombre
            if(binding.editTextSingInNombre.text.isEmpty()){
                binding.editTextSingInNombre.error = resources.getString(R.string.nombre_requerido)
            }
            // Validar apellido
            if(binding.editTextSingInApellido.text.isEmpty()){
                binding.editTextSingInApellido.error = resources.getString(R.string.apellido_requerido)
                return@setOnClickListener
            }
            //Validar telefono
            if(binding.editTextPhone.text.length != 10){
                binding.editTextPhone.error = resources.getString(R.string.telefono_requerido)
                return@setOnClickListener
            }
            // Validar genero

            // Validar direccion
            if(binding.editTextDireccion.text.isEmpty()){
                binding.editTextDireccion.error = resources.getString(R.string.direccion_requerido)
                return@setOnClickListener
            }
            // Validar correo
            if(!isValidEmail(binding.editTextSingInCorreo.text)){
                binding.editTextSingInCorreo.error = resources.getString(R.string.email_no_valid)
                return@setOnClickListener
            }
            //Validar contraseña
            if(binding.editTextSingInPassword.text.length < PASSWORD_LENGHT){
                binding.editTextSingInPassword.error = resources.getString(R.string.password_no_valid)
                return@setOnClickListener
            }
            //Validar confirmación de contraseña
            if(binding.editTextPasswordConfirmation.text.length < 8){
                binding.editTextPasswordConfirmation.error = resources.getString(R.string.password_no_valid)
                return@setOnClickListener
            }
            // Validar igualdad de contraseñas
            if(binding.editTextSingInPassword.text.toString() != binding.editTextPasswordConfirmation.text.toString()){
                binding.editTextSingInPassword.error = resources.getString(R.string.password_no_match)
                return@setOnClickListener
            }

            // Datos
            val nombre = binding.editTextSingInNombre.text.toString()
            val apellido = binding.editTextSingInApellido.text.toString()
            val genero = binding.spinnerGeneros.selectedItem.toString()
            val telefono = binding.editTextPhone.text.toString()
            val direction = binding.editTextDireccion.text.toString()
            val correo = binding.editTextSingInCorreo.text.toString()
            val password = binding.editTextSingInPassword.text.toString()

            val usuario = Usuario(nombre, apellido, genero, telefono, direction, correo)

            // Creamos nuevo usuario
            // Authentication
            signUpNewUser(correo, password)
            val UID_USER = auth.uid.toString()

            // Realtime Database
            realtimeDatabase.child(UID_USER).setValue(usuario).addOnSuccessListener {
                Toast.makeText(baseContext, "Los datos del usuario se han guardado con exito", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(baseContext, "No se pudo guardar los datos del usuario", Toast.LENGTH_SHORT).show()
            }
        }

        // Go Login
        binding.btnGoLogin.setOnClickListener {
            val intention1 = Intent(this,MainActivity::class.java)
            startActivity(intention1)
        }

        // Volver
        binding.imgViewBackBarSingIn.setOnClickListener {
            val intention2 = Intent(this, MainActivity::class.java)
            startActivity(intention2)
        }

    }

    private fun isValidEmail(target: CharSequence): Boolean{
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun signUpNewUser(email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(EXTRA_LOGIN, "createUserWithEmail:success")
                    //Toast.makeText(baseContext, "Nuevo Usuario Guardado", Toast.LENGTH_SHORT).show()
                    //updateUI(user)
                    val intention1 = Intent(this,MainActivity::class.java)
                    startActivity(intention1)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(EXTRA_LOGIN, "createUserWithEmail:failure", task.exception)
                    //Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

}
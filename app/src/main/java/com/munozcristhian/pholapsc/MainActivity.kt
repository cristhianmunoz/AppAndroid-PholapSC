package com.munozcristhian.pholapsc

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.munozcristhian.pholapsc.databinding.ActivityMainBinding
import com.munozcristhian.pholapsc.model.Usuario


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var realtimeDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Initialize Realtime Database
        realtimeDatabase = FirebaseDatabase.getInstance().getReference("Usuarios")

        // Log In
        binding.btnIngresar.setOnClickListener(){
            if(!isValidEmail(binding.editTextCorreoElectronico.text)){
                binding.editTextCorreoElectronico.error = resources.getString(R.string.email_no_valid)
                return@setOnClickListener
            }
            if(binding.editTextPassword.text.length < PASSWORD_LENGHT){
                binding.editTextPassword.error = resources.getString(R.string.password_no_valid)
                return@setOnClickListener
            }

            AutenticarUsuario(binding.editTextCorreoElectronico.text.toString(), binding.editTextPassword.text.toString())
        }

        // Sig In
        binding.btnNoTieneCuenta.setOnClickListener {
            val intention2 = Intent(this, SingInActivity::class.java)
            startActivity(intention2)
        }

    }

    private fun isValidEmail(target: CharSequence): Boolean{
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun AutenticarUsuario(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(EXTRA_LOGIN, "signInWithEmail:success")

                    //Si pasa validación de datos requeridos, ir a pantalla principal
                    val intencion = Intent(this, CategoryActivity::class.java)

                    //Get Usuario
                    val UID_USER = auth.uid.toString()
                    realtimeDatabase.child(UID_USER).get().addOnSuccessListener {
                        Log.i("FIREBASE_REALTIME_DATABASE", "Got value ${it}")
                        val usuario = Usuario(
                            it.child("nombre").value.toString(),
                            it.child("apellido").value.toString(),
                            it.child("genero").value.toString(),
                            it.child("telefono").value.toString(),
                            it.child("direccion").value.toString(),
                            it.child("correo").value.toString()
                        )
                        intencion.putExtra(CURRENT_USER, usuario)
                        startActivity(intencion)
                    }.addOnFailureListener{
                        Log.e("firebase", "Error getting data", it)
                    }


                } else {
                    Log.w(EXTRA_LOGIN, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
    }



}
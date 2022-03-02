package com.munozcristhian.pholapsc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.munozcristhian.pholapsc.databinding.ActivitySessionBinding
import com.munozcristhian.pholapsc.model.Sesion
import com.munozcristhian.pholapsc.model.Usuario
import com.munozcristhian.pholapsc.ui.main.SpinnerAdapter

class SessionActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySessionBinding
    private lateinit var spinnerAdapter: SpinnerAdapter
    private var numeroSesiones: Long = 0

    // Usuario
    private lateinit var uid: String
    private lateinit var usuario: Usuario
    private lateinit var realtimeDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Extras
        val extras = intent.extras
        if (extras != null) {
            usuario = extras.get(CURRENT_USER) as Usuario
            uid = extras.getString(UID_USER) as String
        }else{
            Log.d("SESION_LOG", "No hay extras para Sesion")
        }

        // Initialize Realtime Database
        realtimeDatabase = FirebaseDatabase.getInstance().reference.child("Sesiones").child(uid)
        vertificarSesiones()

        // Volver
        binding.imgViewBackBarSession.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra(CURRENT_USER, usuario)
            intent.putExtra(UID_USER, uid)
            startActivity(intent)
        }

        // Paquetes
        ArrayAdapter.createFromResource(this, R.array.paquetes, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerPaquete.adapter = adapter
        }
        spinnerAdapter = SpinnerAdapter()
        binding.spinnerPaquete.onItemSelectedListener = spinnerAdapter

        // Solicitar Sesión
        binding.btnSolicitar.setOnClickListener {
            // Validar direccion
            if(binding.autoTextUbicacion.text.isEmpty()){
                binding.autoTextUbicacion.error = resources.getString(R.string.direccionSesion_requerido)
                return@setOnClickListener
            }
            // Fecha
            if(binding.editTextDate.text.isEmpty()){
                binding.editTextDate.error = resources.getString(R.string.fechaSesion_requerido)
                return@setOnClickListener
            }
            // Hora
            if(binding.editTextTime.text.isEmpty()){
                binding.editTextTime.error = resources.getString(R.string.horaSesion_requerido)
                return@setOnClickListener
            }
            // Paquete

            // Crear Sesion
            val direccion = binding.autoTextUbicacion.text.toString()
            val fecha = binding.editTextDate.text.toString()
            val hora = binding.editTextTime.text.toString()
            val paquete = binding.spinnerPaquete.selectedItem.toString()
            val session = Sesion(fecha,direccion, hora, paquete)
            // Realtime Database
            realtimeDatabase.child(numeroSesiones.toString()).setValue(session).addOnSuccessListener {
                Toast.makeText(baseContext, "Los datos de la sesión se han guardado con exito", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CategoryActivity::class.java)
                intent.putExtra(CURRENT_USER, usuario)
                intent.putExtra(UID_USER, uid)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(baseContext, "No se pudo guardar los datos de la sesión", Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun vertificarSesiones() {
        realtimeDatabase.get().addOnSuccessListener {
            Log.i("FIREBASE_REALTIME_DATABASE", "NUMERO DE SESIONES: ${it.childrenCount}")
            numeroSesiones = it.childrenCount + 1
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}
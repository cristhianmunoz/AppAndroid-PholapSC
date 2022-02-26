package com.munozcristhian.pholapsc

import android.content.Intent
import android.os.Bundle

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.munozcristhian.pholapsc.databinding.ActivitySessionBinding

class SessionActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySessionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Volver
        binding.imgViewBackBarSession.setOnClickListener {
            val intention = Intent(this, CategoryActivity::class.java)
            startActivity(intention)
        }

        // Solicitar Sesión
        /*
        binding.btnRegistrar.setOnClickListener {
            if(!isValidEmail(binding.editTextSingInCorreo.text)){
                binding.editTextSingInCorreo.error = resources.getString(R.string.email_no_valid)
                return@setOnClickListener
            }

            if(binding.editTextSingInPassword.text.length < PASSWORD_LENGHT){
                binding.editTextSingInPassword.error = resources.getString(R.string.password_no_valid)
                return@setOnClickListener
            }

            if(binding.editTextPasswordConfirmation.text.length < 8){
                binding.editTextPasswordConfirmation.error = resources.getString(R.string.password_no_valid)
                return@setOnClickListener
            }

            if(!binding.editTextSingInPassword.text.toString().equals(binding.editTextPasswordConfirmation.text.toString())){
                binding.editTextSingInPassword.error = resources.getString(R.string.password_no_match)
                return@setOnClickListener
            }

            // Creamos nuevo usuario
            SignUpNewUser(binding.editTextSingInCorreo.text.toString(), binding.editTextSingInPassword.text.toString())

//            val intention = Intent(this, ConfiguracionesActivity::class.java)
//            startActivity(intention)
        }

         */

    }
}
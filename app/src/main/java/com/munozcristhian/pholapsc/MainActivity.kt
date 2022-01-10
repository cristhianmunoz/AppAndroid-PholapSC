package com.munozcristhian.pholapsc

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import com.munozcristhian.pholapsc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIngresar.setOnClickListener(){
            val intent = Intent(this, CategoryActivity::class.java).also {
                startActivity(it)
            }
        }
        var btnSingIn = findViewById<Button>(R.id.btnNoTieneCuenta)
        btnSingIn.setOnClickListener {
            val intention2 = Intent(this, SingInActivity::class.java)
            startActivity(intention2)
        }

    }


}
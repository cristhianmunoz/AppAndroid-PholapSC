package com.munozcristhian.pholapsc

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView

class InfoImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_imagen)
        var btnbackInfoImage = findViewById<ImageView>(R.id.imgViewBackBarInfoImage)
        btnbackInfoImage.setOnClickListener {
            val intention = Intent(this, SeleccionActivity::class.java)
            startActivity(intention)
        }

    }
}
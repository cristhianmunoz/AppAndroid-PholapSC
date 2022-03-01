package com.munozcristhian.pholapsc

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.munozcristhian.pholapsc.databinding.ActivityInfoImagenBinding
import com.squareup.picasso.Picasso


class InfoImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoImagenBinding
    private var posicionImagen: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoImagenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Extras

        val extras = intent.extras
        if (extras != null) {
            posicionImagen = extras.getInt(IMAGEN_SELECCIONADA)
            val texto = "Imagen $posicionImagen"
            binding.textViewNombreFoto.text = texto
            binding.textViewImagenDescripcion.text = WEB_IMAGES[posicionImagen]
            Picasso.get().load(WEB_IMAGES[posicionImagen]).into(binding.imageViewPhoto)
        }else{
            Log.d("INFO_IMAGEN_LOG", "No hay extras para Info Imagen")
        }

        // Back
        binding.imgViewBackBarInfoImage.setOnClickListener {
            val intention = Intent(this, CategoryActivity::class.java)
            startActivity(intention)
        }

    }
}
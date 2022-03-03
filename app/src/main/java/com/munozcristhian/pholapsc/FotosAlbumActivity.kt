package com.munozcristhian.pholapsc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.munozcristhian.pholapsc.databinding.ActivityFotosAlbumBinding
import com.munozcristhian.pholapsc.images.PhotoAdapter


class FotosAlbumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFotosAlbumBinding
    private var fotosAlbum: Array<String> = arrayOf()
    private lateinit var photoAdapter: PhotoAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var nombreAlbum: String = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFotosAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Extras
        val extras = intent.extras
        if (extras != null) {
            fotosAlbum = extras.getStringArray(FOTOS_ALBUM) as Array<String>
            nombreAlbum = extras.getString(NOMBRE_ALBUM) as String
            binding.txtViewTituloAlbum.text = nombreAlbum
        }else{
            Log.d("FOTOS_ALBUM_LOG", "No hay extras para FOTOS_ALBUM")
        }

        photoAdapter = PhotoAdapter(this, fotosAlbum, R.layout.photo_layout)
        //resourceImagesAdapter = ResourceImagesAdapter(this, parties, R.layout.image_layout)

        layoutManager = GridLayoutManager(this, 2)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = photoAdapter

        val context = this

        // Click en imagenes
        photoAdapter.setOnItemClickListener(object: PhotoAdapter.onItemClickListener {
            override fun onItemClick(view: ImageView, position: Int) {
                val intent = Intent(context, InfoImageActivity::class.java)
                intent.putExtra(IMAGEN_SELECCIONADA, position)
                startActivity(intent)
                //Toast.makeText(this@SeleccionActivity, "Has seleccionado la imagen $position.", Toast.LENGTH_SHORT).show()
            }

        })

        // Back
        binding.imgViewBackAlbum.setOnClickListener {
            val intention = Intent(this, CategoryActivity::class.java)
            startActivity(intention)
        }
    }
}
package com.munozcristhian.pholapsc

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.munozcristhian.pholapsc.databinding.SeleccionImpresionBinding
import com.munozcristhian.pholapsc.images.SeleccionAdapter
import com.munozcristhian.pholapsc.model.Usuario


class SeleccionActivity : AppCompatActivity() {

    private lateinit var binding: SeleccionImpresionBinding
    // Storage
    private lateinit var storage: FirebaseStorage
    private lateinit var imagesRef: StorageReference

    // Images
    private lateinit var linksImages: Array<String>
    private lateinit var localImages: IntArray
    private lateinit var seleccionAdapter: SeleccionAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var fotosSeleccionadas: MutableList<Int> = mutableListOf()
    private var fotosFirebase: MutableList<String> = mutableListOf()
    private lateinit var pathFolder: String

    // Usuario
    private lateinit var uid: String
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seleccion_impresion)
        binding = SeleccionImpresionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Extras
        val extras = intent.extras
        if (extras != null) {
            usuario = extras.get(CURRENT_USER) as Usuario
            uid = extras.getString(UID_USER) as String
            pathFolder = "images/$uid"
        }else{
            Log.d("SELECCION_LOG", "No hay extras para Seleccion")
        }

        // Cargar desde Firebase
        //cargarImagenes()
        ingresarFotos()

        binding.imgViewBackImpresion.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(CURRENT_USER, usuario)
            intent.putExtra(UID_USER, uid)
            startActivity(intent)
        }


        binding.imgViewCheck.setOnClickListener {

            if(fotosSeleccionadas.size == 0){
                Toast.makeText(this, "Debe seleccionar al menos 1 fotografía", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            val intent = Intent(this, CategoryActivity::class.java)
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Solicitud de Impresión")
            val mensaje = "Has seleccionado " + fotosSeleccionadas.size + " fotos. \nSus fotografías serán entregadas en máximo 3 días laborables"
            dialogBuilder.setMessage(mensaje)
            dialogBuilder.setPositiveButton("Confirmar") { _, _ ->
                Toast.makeText(this, "Solicitud de impresión realizada con éxito.", Toast.LENGTH_LONG).show()
                //intention.putExtra("fotos", fotosSeleccionadas.toIntArray())
                intent.putExtra(CURRENT_USER, usuario)
                intent.putExtra(UID_USER, uid)
                startActivity(intent)
            }
            dialogBuilder.setNegativeButton("Cancelar") { _, _ ->
                //startActivity(intention)
                //pass
            }
            dialogBuilder.create().show()

        }

        binding.imgViewBackImpresion.setOnClickListener {
            val intention = Intent(this, CategoryActivity::class.java)
            startActivity(intention)
        }

    }
    private fun ingresarFotos() {
        //linksImages = WEB_IMAGES
        linksImages = fotosFirebase.toTypedArray()
        localImages = LOCAL_IMAGES

        seleccionAdapter = SeleccionAdapter(this, FIREBASE_IMAGES.toTypedArray(), R.layout.image_seleccion_layout)
        //resourceImagesAdapter = ResourceImagesAdapter(this, parties, R.layout.image_layout)

        layoutManager = GridLayoutManager(this, 2)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = seleccionAdapter

        // Click en imagenes
        seleccionAdapter.setOnItemClickListener(object: SeleccionAdapter.onItemClickListener {
            override fun onItemClick(view: ImageView, check: CheckBox, position: Int) {
                if(!check.isChecked){
                    check.isChecked = true
                    addFoto(position)
                }else{
                    check.isChecked = false
                }
                //Toast.makeText(this@SeleccionActivity, "Has seleccionado la imagen $position.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun addFoto(view: Int){
        if(!fotosSeleccionadas.any {it == view}){
            fotosSeleccionadas.add(view)
        }
    }
}

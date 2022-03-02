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
import com.munozcristhian.pholapsc.images.OnlineImagesAdapter
import com.munozcristhian.pholapsc.model.Usuario


class SeleccionActivity : AppCompatActivity() {

    private lateinit var binding: SeleccionImpresionBinding
    // Storage
    private lateinit var storage: FirebaseStorage
    private lateinit var imagesRef: StorageReference

    // Images
    private lateinit var linksImages: Array<String>
    private lateinit var localImages: IntArray
    private lateinit var onlineImagesAdapter: OnlineImagesAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var fotosSeleccionadas: MutableList<Int> = mutableListOf()
    private var fotosFirebase: MutableList<String> = mutableListOf()
    private var albumes: MutableList<String> = mutableListOf()
    private lateinit var pathFolder: String
    private var albumesFotos: HashMap<String, MutableList<String>> = HashMap()
    private var contador: Int = 0
    private var numeroFotos: Int = 0

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

        // Firebase Storage
        storage = Firebase.storage
        imagesRef = storage.reference.child(pathFolder)

        // Listar albumes
        imagesRef.listAll().addOnSuccessListener {
            for(prefix in it.prefixes){
                albumes.add(prefix.name)
            }
            //Log.d("SELECCION_LOG", "Lista:  ${albumes}")
        }.addOnFailureListener {
            // Uh-oh, an error occurred!
        }.addOnCompleteListener {
            // Listar fotos por album
            for(album in albumes){
                imagesRef.child(album).listAll().addOnSuccessListener {
                    val fotos: MutableList<String> = mutableListOf()
                    for(image in it.items){
                        fotos.add(image.name)
                    }
                    albumesFotos[album] = fotos
                    numeroFotos += albumesFotos[album]!!.size
                }.addOnFailureListener {
                    // Uh-oh, an error occurred!
                }.addOnCompleteListener {
                    for(album in albumesFotos.keys){
                        for(imagen in albumesFotos[album]!!){
                            // Descargar URL de cada imagen
                            imagesRef.child(album).child(imagen).downloadUrl.addOnSuccessListener {
                                Log.d("SELECCION_LOG", "URL encontrado $it")
                                fotosFirebase.add(it.toString())
                            }.addOnFailureListener {
                                Log.d("SELECCION_LOG", "No se encontro la imagen")
                            }.addOnCompleteListener {
                                contador++
                                if(contador==numeroFotos){
                                    iniciarCargaFotos()
                                }
                            }
                        }
                    }
                }
            }
        }

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

    private fun iniciarCargaFotos() {
        //linksImages = WEB_IMAGES
        linksImages = fotosFirebase.toTypedArray()
        localImages = LOCAL_IMAGES

        onlineImagesAdapter = OnlineImagesAdapter(this, linksImages, R.layout.image_layout)
        //resourceImagesAdapter = ResourceImagesAdapter(this, parties, R.layout.image_layout)

        layoutManager = GridLayoutManager(this, 2)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = onlineImagesAdapter

        // Click en imagenes
        onlineImagesAdapter.setOnItemClickListener(object: OnlineImagesAdapter.onItemClickListener {
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

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
    private val absolutePath: String = "gs://pholapsc.appspot.com/"

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
        }else{
            Log.d("SELECCION_LOG", "No hay extras para Seleccion")
        }

        // Firebase Storage
        storage = Firebase.storage
        imagesRef = storage.reference.child("images/$uid")

        linksImages = WEB_IMAGES
        localImages = LOCAL_IMAGES

        onlineImagesAdapter = OnlineImagesAdapter(this, linksImages, R.layout.image_layout)
        //resourceImagesAdapter = ResourceImagesAdapter(this, parties, R.layout.image_layout)

        layoutManager = GridLayoutManager(this, 2)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = onlineImagesAdapter
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

    private fun addFoto(view: Int){
        if(!fotosSeleccionadas.any {it == view}){
            fotosSeleccionadas.add(view)
        }
    }

    private fun getWebImages(): Array<String> {
        return arrayOf(
            "https://cdn0.bodas.com.mx/articles/images/real-wedding/2/2/1/r10_2x_1150609.jpg",
            "https://cdn0.bodas.net/article-real-wedding/861/3_2/960/jpg/3297409.jpeg",
            "https://www.jimdo.com/static/19ef43414529f3beb6f4c57637eb379f/c8d81/teaser.jpg",
            "https://cdn0.bodas.com.mx/article-real-wedding/193/3_2/960/jpg/1232729.jpeg",
            "https://static3.elcorreo.com/www/multimedia/201908/06/media/cortadas/B%26I-68-kBZE-U80920740627i7B-624x385@El%20Correo.jpg",
            "https://static3.elcorreo.com/www/multimedia/202201/06/media/cortadas/patriciawithlove-957_websize-kp2E-U1604672148838nC-624x385@El%20Correo.jpg",
            "https://media.vogue.mx/photos/618eb3c60fedaa9e9de852f0/4:3/w_1962,h_1472,c_limit/JCV07501.jpg",
            "https://d2lcsjo4hzzyvz.cloudfront.net/blog/wp-content/uploads/2020/10/29155650/matrimonios-durante-el-COVID-19-760x507.jpg",
            "https://www.unabodaoriginal.es/blog/wp-content/uploads/2021/06/bodas-nueva-normalidad-1200x900.jpg",
            "https://firebasestorage.googleapis.com/v0/b/pholapsc.appspot.com/o/images%2FLIleoK1JDRPkTcXUpWYT3lIqovB2%2Falbum1%2Fimg1.jpeg?alt=media&token=cf2a9d7d-4da2-4c66-9035-83784b103eff",
            "https://static.pexels.com/photos/86462/red-kite-bird-of-prey-milan-raptor-86462.jpeg",
            "https://static.pexels.com/photos/67508/pexels-photo-67508.jpeg",
            "https://static.pexels.com/photos/55814/leo-animal-savannah-lioness-55814.jpeg",
            "https://static.pexels.com/photos/40745/red-squirrel-rodent-nature-wildlife-40745.jpeg",
            "https://static.pexels.com/photos/33392/portrait-bird-nature-wild.jpg",
            "https://static.pexels.com/photos/62640/pexels-photo-62640.jpeg",
            "https://static.pexels.com/photos/38438/rattlesnake-toxic-snake-dangerous-38438.jpeg",
            "https://static.pexels.com/photos/33149/lemur-ring-tailed-lemur-primate-mammal.jpg",
        )
    }



    private fun getLocalImages(): IntArray {
        return intArrayOf(
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8,
            R.drawable.img9,
            R.drawable.img10,
            R.drawable.img11,
            R.drawable.img12,
            R.drawable.img13,
            R.drawable.img14,
            R.drawable.img15,
            R.drawable.img16,
            R.drawable.img17,
            R.drawable.img18,
            R.drawable.img19,
            R.drawable.img20,
        )
    }


}
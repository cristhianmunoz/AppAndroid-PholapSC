package com.munozcristhian.pholapsc

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.marginEnd
import androidx.core.view.size
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mayorgaerick.picassoapp.DeviceImagesAdapter
import com.mayorgaerick.picassoapp.ResourceImagesAdapter
import com.munozcristhian.pholapsc.databinding.SeleccionImpresionBinding
import com.munozcristhian.pholapsc.images.OnlineImagesAdapter

class SeleccionActivity : AppCompatActivity() {
    private lateinit var binding: SeleccionImpresionBinding
    private lateinit var checkBoxes: MutableList<View>

    // Images
    private lateinit var animals: Array<String>
    private lateinit var parties: IntArray
    private lateinit var images: MutableList<String>
    private lateinit var onlineImagesAdapter: OnlineImagesAdapter
    private lateinit var resourceImagesAdapter: ResourceImagesAdapter
    private lateinit var deviceImagesAdapter: DeviceImagesAdapter
    private var PERMISSION_READ_EXTERNAL_MEMORY = 1
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var fotosSeleccionadas: MutableList<Int> = mutableListOf()
    private lateinit var checkBoxSelected: CheckBox
    private lateinit var imageViewSelected: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seleccion_impresion)
        binding = SeleccionImpresionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animals = getAnimalsLinks()!!
        parties = getPartyPics()!!

        onlineImagesAdapter = OnlineImagesAdapter(this, animals, R.layout.image_layout)
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
                Toast.makeText(this@SeleccionActivity, "Has seleccionado la imagen $position.", Toast.LENGTH_SHORT).show()
            }

        })


        binding.imgViewBackImpresion.setOnClickListener{
            //val intention = Intent(this, HomeFotosActivity::class.java)
            //startActivity(intention)
        }


        binding.imgViewCheck.setOnClickListener {
            //checkSelectedImages()

            val intention = Intent(this, CategoryActivity::class.java)
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Solicitud de Impresión")
            val mensaje = "Has seleccionado " + fotosSeleccionadas.size + " fotos. \nSus fotografías serán entregadas en máximo 3 días laborables"
            dialogBuilder.setMessage(mensaje)
            dialogBuilder.setPositiveButton("Confirmar", DialogInterface.OnClickListener { _, _ ->
                intention.putExtra("fotos", fotosSeleccionadas.toIntArray())
                startActivity(intention)
                Toast.makeText(this, "Solicitud de impresión realizada con éxito.", Toast.LENGTH_LONG).show()
            })
            dialogBuilder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                //startActivity(intention)
            //pass
            })
            dialogBuilder.create().show()

        }

        binding.imgViewBackImpresion.setOnClickListener {
            val intention = Intent(this, CategoryActivity::class.java)
            startActivity(intention)
        }

    }

    private fun addFoto(view: Int){
        if(!fotosSeleccionadas.any() {it == view}){
            fotosSeleccionadas.add(view)
        }
    }

    private fun getAnimalsLinks(): Array<String>? {
        return arrayOf(
            "https://static.pexels.com/photos/86462/red-kite-bird-of-prey-milan-raptor-86462.jpeg",
            "https://static.pexels.com/photos/67508/pexels-photo-67508.jpeg",
            "https://static.pexels.com/photos/55814/leo-animal-savannah-lioness-55814.jpeg",
            "https://static.pexels.com/photos/40745/red-squirrel-rodent-nature-wildlife-40745.jpeg",
            "https://static.pexels.com/photos/33392/portrait-bird-nature-wild.jpg",
            "https://static.pexels.com/photos/62640/pexels-photo-62640.jpeg",
            "https://static.pexels.com/photos/38438/rattlesnake-toxic-snake-dangerous-38438.jpeg",
            "https://static.pexels.com/photos/33149/lemur-ring-tailed-lemur-primate-mammal.jpg",
            "https://static.pexels.com/photos/1137/wood-animal-dog-pet.jpg",
            "https://static.pexels.com/photos/40731/ladybug-drop-of-water-rain-leaf-40731.jpeg",
            "https://static.pexels.com/photos/40860/spider-macro-insect-arachnid-40860.jpeg",
            "https://static.pexels.com/photos/63282/crab-yellow-ocypode-quadrata-atlantic-ghost-crab-63282.jpeg",
            "https://static.pexels.com/photos/45246/green-tree-python-python-tree-python-green-45246.jpeg",
            "https://static.pexels.com/photos/39245/zebra-stripes-black-and-white-zoo-39245.jpeg",
            "https://static.pexels.com/photos/92000/pexels-photo-92000.jpeg",
            "https://static.pexels.com/photos/121445/pexels-photo-121445.jpeg",
            "https://static.pexels.com/photos/112603/pexels-photo-112603.jpeg",
            "https://static.pexels.com/photos/52961/antelope-nature-flowers-meadow-52961.jpeg",
            "https://static.pexels.com/photos/36450/flamingo-bird-pink-nature.jpg",
            "https://static.pexels.com/photos/20861/pexels-photo.jpg",
            "https://static.pexels.com/photos/54108/peacock-bird-spring-animal-54108.jpeg",
            "https://static.pexels.com/photos/24208/pexels-photo-24208.jpg"
        )
    }



    private fun getPartyPics(): IntArray? {
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

    private fun contSelectedImages(): Int {
        return 0
    }


}
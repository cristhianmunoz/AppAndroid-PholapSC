package com.munozcristhian.pholapsc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.munozcristhian.pholapsc.images.PhotoAdapter
import com.munozcristhian.pholapsc.images.SeleccionAdapter
import com.munozcristhian.pholapsc.model.Usuario


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PhotosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhotosFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //Firebase
    // Storage
    private lateinit var storage: FirebaseStorage
    private lateinit var imagesRef: StorageReference
    //Imagenes
    private lateinit var photoAdapter: PhotoAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var fotosSeleccionada: Int = -1
    private lateinit var linksImages: Array<String>
    private lateinit var localImages: IntArray
    private var fotosSeleccionadas: MutableList<Int> = mutableListOf()
    private var fotosFirebase: MutableList<String> = mutableListOf()
    private var albumes: MutableList<String> = mutableListOf()
    private lateinit var pathFolder: String
    private var albumesFotos: HashMap<String, MutableList<String>> = HashMap()
    private var contador: Int = 0
    private var numeroFotos: Int = 0
    //user
    // Usuario
    private lateinit var uid: String
    private lateinit var usuario: Usuario
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_photos, container, false)
//        linksImages = WEB_IMAGES
//        val baseContext = this.requireContext()
//        photoAdapter = PhotoAdapter(this.requireContext(), linksImages, R.layout.photo_layout)
//        layoutManager = GridLayoutManager(this.requireContext(), 2)
//
        //Extras
        var auth = Firebase.auth
        uid=auth.uid.toString()
        pathFolder="images/$uid"
        //usuario= auth.currentUser
//        val extras = intent.extras
//        if (extras != null) {
//            usuario = extras.get(CURRENT_USER) as Usuario
//            uid = extras.getString(UID_USER) as String
//            pathFolder = "images/$uid"
//        }else{
//            Log.d("SELECCION_LOG", "No hay extras para Seleccion")
//        }
//        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewPhotos)
//        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = layoutManager
//        recyclerView.adapter = photoAdapter
//        BEFORE INFALE
        cargarImagenes()
        // Inflate the layout for this fragment
        return view
    }
    private fun cargarImagenes() {
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
                                    //linksImages = WEB_IMAGES
                                    linksImages = fotosFirebase.toTypedArray()
                                    localImages = LOCAL_IMAGES

                                    photoAdapter = PhotoAdapter(context!!, linksImages, R.layout.photo_layout)
                                    //resourceImagesAdapter = ResourceImagesAdapter(this, parties, R.layout.image_layout)

                                    layoutManager = GridLayoutManager(context, 2)

                                    var recylcerViewFotos:RecyclerView=view!!.findViewById(R.id.recyclerViewPhotos)
                                    recylcerViewFotos.setHasFixedSize(true)
                                    recylcerViewFotos.layoutManager=layoutManager
                                    recylcerViewFotos.adapter=photoAdapter
                                    // Click en imagenes
                                    photoAdapter.setOnItemClickListener(object: PhotoAdapter.onItemClickListener {

                                        override fun onItemClick(view: ImageView, position: Int) {
                                            addFoto(position)
                                        }

                                    })
                                    photoAdapter.setOnItemClickListener(object: PhotoAdapter.onItemClickListener {
                                        override fun onItemClick(view: ImageView, position: Int) {
                                            fotosSeleccionada = position
                                            val intent = Intent(context, InfoImageActivity::class.java)
                                            intent.putExtra(IMAGEN_SELECCIONADA, position)
                                            startActivity(intent)

                                            //Toast.makeText(this@SeleccionActivity, "Has seleccionado la imagen $position.", Toast.LENGTH_SHORT).show()
                                        }

                                    })
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun ingresarFotos() {
        //linksImages = WEB_IMAGES
        linksImages = fotosFirebase.toTypedArray()
        localImages = LOCAL_IMAGES

        photoAdapter = PhotoAdapter(context!!, linksImages, R.layout.photo_layout)
        //resourceImagesAdapter = ResourceImagesAdapter(this, parties, R.layout.image_layout)

        layoutManager = GridLayoutManager(context, 2)

        var recylcerViewFotos:RecyclerView=view!!.findViewById(R.id.recyclerViewPhotos)
        recylcerViewFotos.setHasFixedSize(true)
        recylcerViewFotos.layoutManager=layoutManager
        recylcerViewFotos.adapter=photoAdapter
        // Click en imagenes
        photoAdapter.setOnItemClickListener(object: PhotoAdapter.onItemClickListener {

            override fun onItemClick(view: ImageView, position: Int) {
                addFoto(position)
            }

        })
    }
    private fun addFoto(view: Int){
        if(!fotosSeleccionadas.any {it == view}){
            fotosSeleccionadas.add(view)
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PhotosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PhotosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
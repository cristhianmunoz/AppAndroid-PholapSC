package com.munozcristhian.pholapsc


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.munozcristhian.pholapsc.images.AlbumAdapter
import com.munozcristhian.pholapsc.images.PhotoAdapter
import com.munozcristhian.pholapsc.model.Usuario
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AlbumFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // Storage
    private lateinit var storage: FirebaseStorage
    private lateinit var imagesRef: StorageReference
    //Imagenes
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var albumAdapter: AlbumAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var albumSeleccionado: Int=-1
    private lateinit var linksImages: Array<String>
    private lateinit var linksImagesalbum: Array<String>
    private lateinit var localImages: IntArray
    private var fotosSeleccionadas: MutableList<Int> = mutableListOf()
    private var fotosFirebase: MutableList<String> = mutableListOf()
    private var fotosFirebasealbum: MutableList<String> = mutableListOf()
    private var albumes: MutableList<String> = mutableListOf()
    private lateinit var pathFolder: String
    private var albumesFotos: HashMap<String, MutableList<String>> = HashMap()
    private var albumesFotos1: HashMap<String, MutableList<String>> = HashMap()
    private var contador: Int = 0
    private var numeroFotos: Int = 0
    private var numeroFotos1: Int = 0
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

        val view: View = inflater.inflate(R.layout.fragment_album, container, false)
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_album, container, false)

        //Extras
        var auth = Firebase.auth
        uid=auth.uid.toString()
        pathFolder="images/$uid"

        cargarAlbum()
        // Inflate the layout for this fragment
        return view
    }

    private fun cargarAlbum() {
        // Firebase Storage
        storage = Firebase.storage
        imagesRef = storage.reference.child(pathFolder)

        // Listar albumes
        imagesRef.listAll().addOnSuccessListener {
            for(prefix in it.prefixes){
                albumes.add(prefix.name)
                LISTA_ALBUMES.add(prefix.name)
            }
            //Log.d("SELECCION_LOG", "Lista:  ${albumes}")
        }.addOnFailureListener {
            // Uh-oh, an error occurred!
        }.addOnCompleteListener {
            // Listar fotos por album
            for(album in albumes){
                imagesRef.child(album).list(1).addOnSuccessListener {
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
                                FIREBASE_IMAGES.add(it.toString())
                            }.addOnFailureListener {
                                Log.d("SELECCION_LOG", "No se encontro la imagen")
                            }.addOnCompleteListener {
                                contador++
                                if(contador==numeroFotos){
                                    //linksImages = WEB_IMAGES
                                    linksImages = fotosFirebase.toTypedArray()
                                    //localImages = LOCAL_IMAGES

                                    albumAdapter = AlbumAdapter(context!!, linksImages, R.layout.album_layout)
                                    //resourceImagesAdapter = ResourceImagesAdapter(this, parties, R.layout.image_layout)

                                    layoutManager = GridLayoutManager(context, 2)

                                    var recylcerViewFotos:RecyclerView=view!!.findViewById(R.id.recyclerViewAlbum)
                                    recylcerViewFotos.setHasFixedSize(true)
                                    recylcerViewFotos.layoutManager=layoutManager
                                    recylcerViewFotos.adapter=albumAdapter
                                    // Click en imagenes
                                    albumAdapter.setOnItemClickListener(object: AlbumAdapter.onItemClickListener {

                                        override fun onItemClick(view: ImageView, text: TextView, position: Int) {
                                            //addFoto(position)
                                        }

                                    })
                                    albumAdapter.setOnItemClickListener(object: AlbumAdapter.onItemClickListener {
                                        override fun onItemClick(view: ImageView,text:TextView, position: Int) {
                                            cargarImagenesalbum()
                                            albumSeleccionado= position
                                            val intent = Intent(context, FotosAlbumActivity::class.java).apply {
                                                putExtra(FOTOS_ALBUM,linksImages)
                                                putExtra(NOMBRE_ALBUM,albumes[albumSeleccionado])
                                            }
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

    private fun cargarImagenesalbum() {
        // Firebase Storage
        storage = Firebase.storage
        imagesRef = storage.reference.child(pathFolder)

        // Listar albumes
        imagesRef.listAll().addOnSuccessListener {
            for (prefix in it.prefixes) {
                albumes.add(prefix.name)
            }
            //Log.d("SELECCION_LOG", "Lista:  ${albumes}")
        }.addOnFailureListener {
            // Uh-oh, an error occurred!
        }.addOnCompleteListener {
            // Listar fotos por album
            for (album in albumes) {
                imagesRef.child(album).listAll().addOnSuccessListener {
                    val fotos: MutableList<String> = mutableListOf()
                    for (image in it.items) {
                        fotos.add(image.name)
                    }
                    albumesFotos[album] = fotos
                    numeroFotos += albumesFotos[album]!!.size
                }.addOnFailureListener {
                    // Uh-oh, an error occurred!
                }.addOnCompleteListener {
                    for (album in albumesFotos.keys) {
                        for (imagen in albumesFotos[album]!!) {
                            // Descargar URL de cada imagen
                            imagesRef.child(album).child(imagen).downloadUrl.addOnSuccessListener {
                                Log.d("SELECCION_LOG", "URL encontrado $it")
                                fotosFirebasealbum.add(it.toString())
                            }.addOnFailureListener {
                                Log.d("SELECCION_LOG", "No se encontro la imagen")
                            }.addOnCompleteListener {
                                contador++
                                if (contador == numeroFotos) {
                                    //linksImages = WEB_IMAGES
                                    linksImages = fotosFirebase.toTypedArray()
                                    //localImages = LOCAL_IMAGES
                                }
                            }
                        }
                    }
                }
            }
        }
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
         * @return A new instance of fragment AlbumFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlbumFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
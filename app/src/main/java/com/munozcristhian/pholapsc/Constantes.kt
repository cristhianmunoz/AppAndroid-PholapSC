package com.munozcristhian.pholapsc

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.munozcristhian.pholapsc.model.Usuario

const val EXTRA_LOGIN = "EXTRA_LOGIN"
const val LOGIN_KEY = "LOGIN_KEY"
const val PASSWORD_KEY = "PASSWORD_KEY"
const val SHAREDINFO_FILENAME = "mySharedInformation.dat"
const val PASSWORD_LENGHT = 8
const val CURRENT_USER = "CURRENT_USER"
const val UID_USER = "UID_USER"
const val IMAGEN_SELECCIONADA = "IMAGEN_SELECCIONADA"
const val FOTOS_ALBUM = "FOTOS_ALBUM"
const val NOMBRE_ALBUM = "NOMBRE_ALBUM"


var CURRENT_USUARIO = Usuario()
var CURRENT_UID = String()
var FIREBASE_AUTH: FirebaseAuth = Firebase.auth
var FIREBASE_IMAGES: MutableList<String> = mutableListOf()
var LISTA_ALBUMES: MutableList<String> = mutableListOf()

val WEB_IMAGES = arrayOf(
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
    "https://static.pexels.com/photos/55814/leo-animal-savannah-lioness-55814.jpeg",
    "https://static.pexels.com/photos/40745/red-squirrel-rodent-nature-wildlife-40745.jpeg",
    "https://static.pexels.com/photos/33392/portrait-bird-nature-wild.jpg",
    "https://static.pexels.com/photos/62640/pexels-photo-62640.jpeg",
    "https://static.pexels.com/photos/38438/rattlesnake-toxic-snake-dangerous-38438.jpeg",
    "https://static.pexels.com/photos/33149/lemur-ring-tailed-lemur-primate-mammal.jpg",
)

val LOCAL_IMAGES = intArrayOf(
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
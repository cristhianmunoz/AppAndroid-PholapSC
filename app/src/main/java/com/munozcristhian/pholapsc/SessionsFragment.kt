package com.munozcristhian.pholapsc

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.munozcristhian.pholapsc.model.Sesion
import com.munozcristhian.pholapsc.sessions.SessionsAdapter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SessionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SessionsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var sesiones:ArrayList<Sesion> = arrayListOf<Sesion>();
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
         this.sesiones.clear()
         val view = inflater.inflate(R.layout.fragment_sessions, container, false);
         //Obtener UID
         var auth = Firebase.auth
         val uid =  auth.uid.toString()
         print("uid = $uid")
         val database = Firebase.database
         val dbRef = database.getReference("Sesiones");
         dbRef.child(uid).get().addOnSuccessListener {
             Log.i("firebase", "Got value ${it.value}")
             var sesioneslist:ArrayList<Sesion>  = it.value as ArrayList<Sesion>
             val l =it.getValue<ArrayList<Sesion>>();
             for(i in l!!.indices){
                 if(l!![i]!=null){
                     Log.i("sesion",l!![i].toString())
                     this.sesiones.add(l!![i])
                 }
             }
             Log.i("seseiones", " ${sesiones}")
         }.addOnFailureListener{
             Log.e("firebase", "Error getting data", it)
         }.addOnCompleteListener {
             var recylcerViewSesiones:RecyclerView= view.findViewById(R.id.recyclerViewListSessions)
             recylcerViewSesiones.layoutManager=LinearLayoutManager(context)
             recylcerViewSesiones.adapter=SessionsAdapter(context!!,this.sesiones){
                 val idSesion=it+1
                 dbRef.child(uid).child(idSesion.toString()).removeValue().addOnSuccessListener {
                     Toast.makeText(context,"Se canceló la sesión de manera exitosa.",Toast.LENGTH_LONG).show()
                 }
             }
         }
         return view
    }

    override fun onStart() {
        super.onStart()
        this.sesiones.clear()
    }
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SessionsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SessionsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
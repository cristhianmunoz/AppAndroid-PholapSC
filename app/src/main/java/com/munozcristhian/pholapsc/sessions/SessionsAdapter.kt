package com.munozcristhian.pholapsc.sessions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.munozcristhian.pholapsc.R
import com.munozcristhian.pholapsc.model.Sesion


class SessionsAdapter(context: Context,listaSesiones:ArrayList<Sesion>):RecyclerView.Adapter<SessionsAdapter.ViewHolder>() {
    private val context:Context=context
    private val listaSesiones:ArrayList<Sesion> = listaSesiones

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var fechaSesion:TextView=itemView.findViewById(R.id.txtViewFecha) as TextView
        var horaSesion:TextView=itemView.findViewById(R.id.txtViewHoraSessionList) as TextView
        var direccionSesion:TextView=itemView.findViewById(R.id.txtViewDireccionSessionLista) as TextView
        var paqueteSesion:TextView=itemView.findViewById(R.id.txtViewPaqueteListaSesion) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v:View = LayoutInflater.from(context).inflate(R.layout.session_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        print(listaSesiones[position]);
        holder.fechaSesion.text=listaSesiones[position].fecha
        holder.horaSesion.text=listaSesiones[position].hora
        holder.direccionSesion.text=listaSesiones[position].direccion
        holder.paqueteSesion.text=listaSesiones[position].paquete
    }

    override fun getItemCount(): Int {
        return this.listaSesiones.size
    }
}
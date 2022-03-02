package com.munozcristhian.pholapsc.images

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.munozcristhian.pholapsc.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class SeleccionAdapter(context: Context, animals: Array<String>, layout: Int) : RecyclerView.Adapter<SeleccionAdapter.ViewHolder>() {
    private val context: Context = context
    private val animals: Array<String> = animals
    private val layout: Int = layout
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(view: ImageView, check: CheckBox, position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(context).inflate(layout, parent, false)
        return ViewHolder(v, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(animals[position]).fit()
            .placeholder(R.drawable.spinner)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.image, object : Callback {
                override fun onSuccess() {
                    //
                }
                override fun onError(e: Exception?) {
                    //Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun getItemCount(): Int {
        return animals.size
    }

    class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.imageViewLayout) as ImageView
        var checkbox: CheckBox = itemView.findViewById(R.id.checkboxImage) as CheckBox


        init {
            itemView.setOnClickListener {
                listener.onItemClick(image, checkbox, adapterPosition)
            }

        }
    }
}

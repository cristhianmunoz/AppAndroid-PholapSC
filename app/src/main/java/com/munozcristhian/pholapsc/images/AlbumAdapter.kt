package com.munozcristhian.pholapsc.images

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.munozcristhian.pholapsc.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class AlbumAdapter (context: Context, animals: Array<String>, layout: Int) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {
    private val context: Context = context
    private val animals: Array<String> = animals
    private val layout: Int = layout
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(view: ImageView, text: TextView, position: Int)
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
        var image: ImageView = itemView.findViewById(R.id.imageViewLayout1) as ImageView
        var textView: TextView = itemView.findViewById(R.id.textNombreAlbum) as TextView


        init {
            itemView.setOnClickListener {
                listener.onItemClick(image, textView, adapterPosition)
            }

        }
    }
}

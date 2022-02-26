package com.mayorgaerick.picassoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.munozcristhian.pholapsc.R
import com.squareup.picasso.Picasso


class DeviceImagesAdapter(context: Context, images: List<String>, layout: Int) :
    RecyclerView.Adapter<DeviceImagesAdapter.ViewHolder>() {
    private val context: Context = context
    private val images: List<String> = images
    private val layout: Int = layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(context).inflate(layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(images[position]).fit()
            .placeholder(R.drawable.spinner)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.imageViewLayout) as ImageView

    }

}


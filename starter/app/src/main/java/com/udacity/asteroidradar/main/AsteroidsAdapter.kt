package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.bindAsteroidStatusImage
import com.udacity.asteroidradar.uimodel.Asteroid

class AsteroidsAdapter(private val listener: (Asteroid) -> Unit) :
    RecyclerView.Adapter<AsteroidsAdapter.Holder>() {

    var data = listOf<Asteroid>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewHolder = Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_view, parent, false)
        )
        viewHolder.itemView.setOnClickListener { listener.invoke(data[viewHolder.adapterPosition]) }
        return viewHolder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvAsteroidName.text = data[position].codename
        holder.tvData.text = data[position].closeApproachDate
        holder.ivHazardous.bindAsteroidStatusImage(data[position].isPotentiallyHazardous)
        val contentDescriptionRes = if (data[position].isPotentiallyHazardous) {
           R.string.potentially_hazardous_asteroid_image
        } else {
            R.string.not_hazardous_asteroid_image
        }
        holder.ivHazardous.contentDescription = holder.ivHazardous.context.getString(contentDescriptionRes)
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAsteroidName: TextView = view.findViewById(R.id.tvAsteroidName)
        val tvData: TextView = view.findViewById(R.id.tvDate)
        val ivHazardous: ImageView = view.findViewById(R.id.ivHazardous)
    }
}
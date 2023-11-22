package com.example.finaldap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerAdapter(
    NBAlist: ArrayList<EquipoNBA>,
    private val onDeleteClick : (Int)->Unit,
    private val onEditClick : (Int) -> Unit,
    private val onItemClick: (Int) -> Unit

): RecyclerView.Adapter<RecyclerAdapter.NBAViewHolder>(){
    private var NBAlist: ArrayList<EquipoNBA>

    init {
        this.NBAlist = NBAlist
    }

    class NBAViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val team= view.findViewById<TextView>(R.id.teamName)
        val position= view.findViewById<TextView>(R.id.teamPosition)
        val photo = view.findViewById<ImageView>(R.id.foto)
        val editar = view.findViewById<Button>(R.id.btnEdit)
        val eliminar = view.findViewById<Button>(R.id.btnDelete)

        fun render(NBAteam: EquipoNBA){
            team.text = NBAteam.teamName
            position.text = NBAteam.teamPosition

            Glide.with(photo.context).load(NBAteam.photo).into(photo)

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NBAViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NBAViewHolder(layoutInflater.inflate(R.layout.item_equipos, parent, false))
    }
    override fun onBindViewHolder(holder: NBAViewHolder, position: Int) {
        val item = NBAlist[position]
        holder.render(item)
        holder.eliminar.setOnClickListener {
            onDeleteClick(position)
        }
        holder.editar.setOnClickListener {
            onEditClick(position)
        }
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
        holder.photo.setOnClickListener {
            onItemClick(position)
        }
    }
    override fun getItemCount(): Int = NBAlist.size

}
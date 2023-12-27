package com.example.appsmartcupon

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.appsmartcupon.poko.Categoria

class CategoriasAdapter()
    : RecyclerView.Adapter<CategoriasAdapter.ViewHolderCategorias>(){
    var context: Context? = null
    lateinit var categorias: ArrayList<Categoria>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCategorias {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolderCategorias(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolderCategorias, position: Int) {
         holder.itemCategoria.text = categorias.get(position).categoria
         holder.btnIr.setOnClickListener{
             val irCategorias = Intent(context, CategoriasActivity::class.java).apply {
                 putExtra("idCategoria", categorias.get(position).idCategoria)
             }
             context?.startActivity(irCategorias)
         }
    }

    override fun getItemCount(): Int {
        return categorias!!.count()
    }

    class ViewHolderCategorias(itemView : View) : RecyclerView.ViewHolder(itemView){

        val itemCategoria : TextView = itemView.findViewById(R.id.itemCategoria)
        val btnIr : TextView = itemView.findViewById(R.id.btnIr)
    }
}
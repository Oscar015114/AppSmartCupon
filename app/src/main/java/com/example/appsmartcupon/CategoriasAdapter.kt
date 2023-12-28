package com.example.appsmartcupon

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.appsmartcupon.interfaces.NotificacionCategoriaLista
import com.example.appsmartcupon.poko.Categoria

class CategoriasAdapter(val categorias: ArrayList<Categoria>, val observador: NotificacionCategoriaLista):
    RecyclerView.Adapter<CategoriasAdapter.ViewHolderCategorias>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCategorias {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_categorias, parent,false)
        return ViewHolderCategorias(itemView)
    }

    override fun getItemCount(): Int {
        return categorias.size
    }

    override fun onBindViewHolder(holder: ViewHolderCategorias, position: Int) {
        val categoria = categorias[position]
        holder.tvNombreCategoria.text = categoria.categoria

        holder.biIrCategoria.setOnClickListener {
            observador.clickItemListaCategoria(position, categoria)
        }

    }

    class ViewHolderCategorias(itemView: View): RecyclerView.ViewHolder(itemView) {

        val tvNombreCategoria: TextView = itemView.findViewById(R.id.tv_nombre_categoria)
        val biIrCategoria: ImageButton = itemView.findViewById(R.id.ib_ir_categoria)

    }

}
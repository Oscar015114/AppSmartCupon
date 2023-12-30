package com.example.appsmartcupon.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appsmartcupon.R
import com.example.appsmartcupon.poko.Sucursal

class SucursalesAdapter (val sucursales: ArrayList<Sucursal>):
    RecyclerView.Adapter<SucursalesAdapter.ViewHolderSucursales>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSucursales {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_sucursales, parent, false)
        return ViewHolderSucursales(itemView)
    }

    override fun getItemCount(): Int {
        return sucursales.size
    }

    override fun onBindViewHolder(holder: ViewHolderSucursales, position: Int) {

        val sucursal = sucursales[position]
        holder.tvNombreSucursal.text = sucursal.nombre
        holder.tvDireccionSucursal.text = "Dirección: ${sucursal.direccion}"
        holder.tvTelefonoSucursal.text = "Teléfono: ${sucursal.telefono}"
        holder.tvCodigoPostal.text = "Código postal: ${sucursal.codigoPostal}"

    }

    class ViewHolderSucursales(itemView: View): RecyclerView.ViewHolder(itemView) {

        val tvNombreSucursal: TextView = itemView.findViewById(R.id.tv_nombre_sucursal)
        val tvDireccionSucursal: TextView = itemView.findViewById(R.id.tv_dirrecion_sucursal)
        val tvTelefonoSucursal: TextView = itemView.findViewById(R.id.tv_telefono_sucursal)
        val tvCodigoPostal: TextView = itemView.findViewById(R.id.tv_codigo_postal_sucursal)

    }

}
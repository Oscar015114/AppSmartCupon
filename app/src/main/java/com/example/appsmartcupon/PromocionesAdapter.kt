package com.example.appsmartcupon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.appsmartcupon.interfaces.NotificacionPromocionLista
import com.example.appsmartcupon.poko.Promocion
import com.example.appsmartcupon.util.Constantes

class PromocionesAdapter (val promociones: ArrayList<Promocion>, val observador: NotificacionPromocionLista):
    RecyclerView.Adapter<PromocionesAdapter.ViewHolderPromociones>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPromociones {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_promociones, parent, false)
        return ViewHolderPromociones(itemView)
    }

    override fun getItemCount(): Int {
        return promociones.size
    }

    override fun onBindViewHolder(holder: ViewHolderPromociones, position: Int) {
        val promocion = promociones[position]
        holder.tvNombreEmpresa.text = promocion.nombreEmpresa
        holder.tvNombrePromocion.text = promocion.nombre
        holder.tvTipoDescuento.text = "Tipo promoción: ${promocion.tipoPromocion}"
        holder.tvCuponesDisponibles.text = "Cupones disponibles: ${promocion.noCuponesMaximo}"
        holder.tvFechaVigencia.text = "Vigencia: ${promocion.fechaFin}"

        if (promocion.tipoPromocion  == Constantes.PROMOCION_DESCUENTO) {
            holder.tvCantidadDescuento.text = "${promocion.porcentajeDescuento}% de descuento"
        } else {
            holder.tvCantidadDescuento.text = "${promocion.precioRebajado} pesos de rebaja"
        }

        holder.cardItem.setOnClickListener {
            observador.clickItemListaPromocion(position, promocion)
        }

    }

    class ViewHolderPromociones(itemView: View): RecyclerView.ViewHolder(itemView) {

        val tvNombreEmpresa: TextView = itemView.findViewById(R.id.tv_empresa)
        val tvNombrePromocion: TextView = itemView.findViewById(R.id.tv_nombrePromocion)
        val tvTipoDescuento: TextView = itemView.findViewById(R.id.tv_tipoPromocion)
        val tvCantidadDescuento: TextView = itemView.findViewById(R.id.tv_cantidadDescuento)
        val tvCuponesDisponibles: TextView = itemView.findViewById(R.id.tv_cuponesDisponible)
        val tvFechaVigencia: TextView = itemView.findViewById(R.id.tv_vigencia)
        val cardItem: CardView = itemView.findViewById(R.id.card_promocion_item)

    }

}
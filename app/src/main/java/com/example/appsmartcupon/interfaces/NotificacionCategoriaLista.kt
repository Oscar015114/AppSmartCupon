package com.example.appsmartcupon.interfaces

import com.example.appsmartcupon.poko.Categoria

interface NotificacionCategoriaLista {
    fun clickItemListaCategoria(posicion: Int, categoria: Categoria)
}
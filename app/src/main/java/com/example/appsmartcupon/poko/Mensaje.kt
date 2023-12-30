package com.example.appsmartcupon.poko

class Mensaje {

    var error: Boolean = false
    var contenido: String = ""
    lateinit var categorias: ArrayList<Categoria>
    lateinit var promociones: ArrayList<Promocion>
    lateinit var promocion: Promocion
    lateinit var sucursales: ArrayList<Sucursal>

}
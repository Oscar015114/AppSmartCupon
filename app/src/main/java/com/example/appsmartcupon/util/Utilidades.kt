package com.example.appsmartcupon.util

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

object Utilidades {

    const val EMAIL_REGEX: String = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    const val TELEFONO_REGEX: String = "^\\d{10}$"
    const val NUMERO_REGEX: String = "^[1-9]\\d*$"

    fun validarCadena(email: String, regex: String): Boolean {
        return !email.matches(Regex(regex))
    }

    fun validarFecha(fecha: String): Boolean {
        try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val fechaLocal = LocalDate.parse(fecha, formatter)
            return false
        } catch (e: org.threeten.bp.format.DateTimeParseException) {
            return true
        }
    }

}